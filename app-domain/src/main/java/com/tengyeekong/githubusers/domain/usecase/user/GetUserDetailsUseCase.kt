package com.tengyeekong.githubusers.domain.usecase.user

import com.tengyeekong.githubusers.common.IoDispatcher
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.domain.repository.UserRepository
import com.tengyeekong.githubusers.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<String, User>(coroutineDispatcher) {

    override fun execute(parameters: String) = userRepository.getUserDetails(parameters)
}
