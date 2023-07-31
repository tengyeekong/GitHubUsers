package com.tengyeekong.githubusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tengyeekong.githubusers.data.datasource.api.UserApi
import com.tengyeekong.githubusers.data.datasource.local.AppDatabase
import com.tengyeekong.githubusers.data.model.UserModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val userApi: UserApi,
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, UserModel>() {

    private val userDao = appDatabase.getUserDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserModel>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // You must explicitly check if the last item is null when
                    // appending, since passing null to userApi is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val response = userApi.getUsers(loadKey ?: 0)

            if (response.isSuccessful) {
                val body = response.body()?.map { it.mapToEntity() }
                appDatabase.withTransaction {
                    // Insert new users into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.
                    body?.let { userDao.insertAll(it) }
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.body()?.isEmpty() ?: true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}