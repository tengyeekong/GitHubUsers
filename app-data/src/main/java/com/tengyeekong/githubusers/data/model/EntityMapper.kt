package com.tengyeekong.githubusers.data.model

interface EntityMapper<T : Any> {
    fun mapToEntity(): T

    fun mapToRoomEntity(): T
}