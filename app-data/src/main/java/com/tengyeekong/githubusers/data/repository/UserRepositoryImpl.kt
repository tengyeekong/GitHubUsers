package com.tengyeekong.githubusers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tengyeekong.githubusers.data.datasource.api.UserApi
import com.tengyeekong.githubusers.data.datasource.local.dao.UserDao
import com.tengyeekong.githubusers.data.model.response.UserDetailsResponse
import com.tengyeekong.githubusers.data.repository.adapter.ResponseResultAdapter
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userRemoteMediator: UserRemoteMediator
) : UserRepository() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(query: String): Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = if (query.isEmpty()) userRemoteMediator else null
    ) {
        userDao.searchUsers(query)
    }.flow.map { paging ->
        paging.map { it }
    }

    override fun getUserDetails(username: String): Flow<User> {
        return object : ResponseResultAdapter<UserDetailsResponse, User>() {
            override suspend fun callApi(): Response<UserDetailsResponse> =
                userApi.getUserDetails(username)

            override suspend fun saveResponseData(response: UserDetailsResponse) {
                userDao.updateUser(response.mapToRoomEntity().copy(note = userDao.getUserSuspend(username)?.note))
            }

            override fun retrieveLocalData(): Flow<User> =
                userDao.getUser(username)
        }.asFlow()
    }

    override suspend fun updateNote(username: String, note: String) = userDao.updateNote(username, note)
}
