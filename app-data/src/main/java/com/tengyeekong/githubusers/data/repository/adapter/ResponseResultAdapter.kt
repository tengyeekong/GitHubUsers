package com.tengyeekong.githubusers.data.repository.adapter

import com.tengyeekong.githubusers.data.datasource.api.ApiBuilder
import com.tengyeekong.githubusers.data.model.EntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * A repository which provides result from remote end point.
 *
 * @param RESPONSE represents the type for response.
 * @param RESULT represents the type of returning data.
 */
abstract class ResponseResultAdapter<RESPONSE : EntityMapper<RESULT>, RESULT : Any> {

    @OptIn(FlowPreview::class)
    fun asFlow() = flowOf(
        flow<RESULT> {
            try {
                // Start calling API
                val response = ApiBuilder.retryIO { callApi() }

                // Check for response validation
                if (response.isSuccessful) {

                    // Parse body
                    val body = response.body()
                    try {
                        // Return success
                        body?.let { saveResponseData(it) }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Exception occurred! Return error
            }
        }.flowOn(Dispatchers.IO),
        retrieveLocalData().flowOn(Dispatchers.IO)
    ).flattenMerge()

    /**
     * Fetches [Response] from the remote end point.
     */
    protected abstract suspend fun callApi(): Response<RESPONSE>

    /**
     * Saves retrieved from [RESPONSE] into Room table.
     */
    protected abstract suspend fun saveResponseData(response: RESPONSE)

    /**
     * Fetches [RESULT] from Room table.
     */
    protected abstract fun retrieveLocalData(): Flow<RESULT>
}
