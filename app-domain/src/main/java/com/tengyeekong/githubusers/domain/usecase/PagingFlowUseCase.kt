package com.tengyeekong.githubusers.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Flow<R>].
 */
abstract class PagingFlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<R> = execute(parameters)
        .flowOn(dispatcher)

    protected abstract fun execute(parameters: P): Flow<R>
}
