package com.tengyeekong.githubusers.domain.usecase.user

import com.tengyeekong.githubusers.common.IoDispatcher
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.domain.repository.UserRepository
import com.tengyeekong.githubusers.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserNoteUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Pair<String, String>, User>(coroutineDispatcher) {

    override fun execute(parameters: Pair<String, String>): Flow<User> = flow {
        userRepository.updateNote(parameters.first, parameters.second)
    }
}
