package com.tengyeekong.githubusers.data.datasource.api

import com.squareup.moshi.*
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

object ApiBuilder {

    fun <T> build(moshi: Moshi, dataSourceClass: Class<T>): T {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .client(httpClient.build())
            .build()

        return retrofit.create(dataSourceClass)
    }

    suspend fun <T> retryIO(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 2_000,
        maxDelay: Long = 10_000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }

    object VoidJsonAdapter : Any() {
        @FromJson
        @Throws(IOException::class)
        fun fromJson(reader: JsonReader): Void? {
            return reader.nextNull()
        }

        @ToJson
        @Throws(IOException::class)
        fun toJson(writer: JsonWriter, v: Void?) {
            writer.nullValue()
        }
    }
}
