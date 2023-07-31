package com.tengyeekong.githubusers.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tengyeekong.githubusers.data.datasource.local.dao.UserDao
import com.tengyeekong.githubusers.data.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}
