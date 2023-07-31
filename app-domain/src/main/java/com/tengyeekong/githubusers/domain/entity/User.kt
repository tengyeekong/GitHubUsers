package com.tengyeekong.githubusers.domain.entity

interface User {
    val id: Int
    val login: String?
    val avatarUrl: String?
    val htmlUrl: String?
    val name: String?
    val bio: String?
    val blog: String?
    val company: String?
    val email: String?
    val followers: Int
    val following: Int
    val note: String?
}
