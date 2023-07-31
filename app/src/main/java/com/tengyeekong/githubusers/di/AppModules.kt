package com.tengyeekong.githubusers.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tengyeekong.githubusers.data.datasource.api.ApiBuilder
import com.tengyeekong.githubusers.data.datasource.api.UserApi
import com.tengyeekong.githubusers.data.datasource.local.AppDatabase
import com.tengyeekong.githubusers.data.datasource.local.dao.UserDao
import com.tengyeekong.githubusers.data.repository.UserRepositoryImpl
import com.tengyeekong.githubusers.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CoroutinesModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class AppModules {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    companion object {

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
            .add(ApiBuilder.VoidJsonAdapter).build()

        @Provides
        @Singleton
        fun provideUserApi(moshi: Moshi): UserApi = ApiBuilder.build(moshi, UserApi::class.java)

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }

        @Provides
        @Singleton
        fun provideChannelDao(appDatabase: AppDatabase): UserDao {
            return appDatabase.getUserDao()
        }
    }
}
