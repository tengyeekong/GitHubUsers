package com.tengyeekong.githubusers.ui

import com.tengyeekong.githubusers.domain.repository.UserRepository
import com.tengyeekong.githubusers.domain.usecase.user.GetUsersUseCase
import com.tengyeekong.githubusers.ui.userlist.UsersViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class UsersTest {

    private val userRepository = mockk<UserRepository>()
    private val getUsersUseCase = GetUsersUseCase(userRepository, Dispatchers.IO)
    private val viewModel = UsersViewModel(getUsersUseCase)

    @Test
    fun `query should be updated with the correct text`() {
        val query = "this is a test query"
        viewModel.updateQuery(query)
        assertEquals(query, viewModel.queryFlow.value)
    }
}