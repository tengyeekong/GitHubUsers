package com.tengyeekong.githubusers.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tengyeekong.githubusers.domain.entity.User

@Keep
@Entity(tableName = "User")
data class UserModel(
    @PrimaryKey
    override val id: Int = 0,
    override val login: String? = null,
    override val avatarUrl: String? = null,
    override val htmlUrl: String? = null,
    override val name: String? = null,
    override val bio: String? = null,
    override val blog: String? = null,
    override val company: String? = null,
    override val email: String? = null,
    override val followers: Int = 0,
    override val following: Int = 0,
    override val note: String? = null,
) : User
