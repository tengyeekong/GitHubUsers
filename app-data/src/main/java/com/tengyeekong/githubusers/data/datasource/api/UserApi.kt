package com.tengyeekong.githubusers.data.datasource.api

import com.tengyeekong.githubusers.data.model.response.UserDetailsResponse
import com.tengyeekong.githubusers.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int,
    ): Response<List<UserResponse>>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String,
    ): Response<UserDetailsResponse>
}
