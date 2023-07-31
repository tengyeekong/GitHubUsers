package com.tengyeekong.githubusers.domain.usecase.user

import androidx.paging.PagingData
import com.tengyeekong.githubusers.common.IoDispatcher
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.domain.repository.UserRepository
import com.tengyeekong.githubusers.domain.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : PagingFlowUseCase<Any, PagingData<User>>(coroutineDispatcher) {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun execute(parameters: Any): Flow<PagingData<User>> = _query
        .debounce(300)
        .flatMapLatest { query ->
            userRepository.getUsers(query)
        }

    fun updateQuery(query: String) {
        _query.value = query
    }
}
