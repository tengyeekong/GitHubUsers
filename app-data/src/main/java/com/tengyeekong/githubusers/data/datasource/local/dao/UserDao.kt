package com.tengyeekong.githubusers.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tengyeekong.githubusers.data.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<UserModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserModel)

    @Query("SELECT * FROM User WHERE login LIKE '%' || :query || '%' OR note LIKE '%' || :query || '%' ORDER BY id")
    fun searchUsers(query: String): PagingSource<Int, UserModel>

    @Query("SELECT * FROM User WHERE login = :username")
    fun getUser(username: String): Flow<UserModel>

    @Query("UPDATE User SET note = :note WHERE login = :username")
    suspend fun updateNote(username: String, note: String)
}