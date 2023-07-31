package com.tengyeekong.githubusers.data.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.tengyeekong.githubusers.data.model.EntityMapper
import com.tengyeekong.githubusers.data.model.UserModel
import com.tengyeekong.githubusers.domain.entity.User

@Keep
data class UserResponse(
    val id: Int,
    val login: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "html_url")
    val htmlUrl: String?
) : EntityMapper<User> {

    override fun mapToEntity() = mapToRoomEntity()

    override fun mapToRoomEntity() = UserModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )
}
