package com.tengyeekong.githubusers.data.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.tengyeekong.githubusers.data.model.EntityMapper
import com.tengyeekong.githubusers.data.model.UserModel
import com.tengyeekong.githubusers.domain.entity.User

@Keep
data class UserDetailsResponse(
    val id: Int,
    val login: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    val name: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val email: String?,
    val followers: Int,
    val following: Int,
) : EntityMapper<User> {

    override fun mapToEntity() = mapToRoomEntity()

    override fun mapToRoomEntity() = UserModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        name = name,
        bio = bio,
        blog = blog,
        company = company,
        email = email,
        followers = followers,
        following = following,
    )
}