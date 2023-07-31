package com.tengyeekong.githubusers.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tengyeekong.githubusers.domain.usecase.user.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    /**
     * User list pagination
     */
    val usersPagingFlow = getUsersUseCase(Any()).cachedIn(viewModelScope)

    /**
     * Flow to get query value
     */
    val queryFlow = getUsersUseCase.query

    /**
     * Update query value and get new user list
     * @param query value to query users
     */
    fun updateQuery(query: String) {
        getUsersUseCase.updateQuery(query)
    }
}
