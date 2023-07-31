package com.tengyeekong.githubusers.domain.repository

import androidx.paging.PagingData
import com.tengyeekong.githubusers.domain.entity.User
import kotlinx.coroutines.flow.Flow

abstract class UserRepository {

    abstract fun getUsers(query: String): Flow<PagingData<User>>

    abstract fun getUserDetails(username: String): Flow<User>

    abstract suspend fun updateNote(username: String, note: String)
}
