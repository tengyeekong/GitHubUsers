package com.tengyeekong.githubusers.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengyeekong.githubusers.domain.usecase.user.GetUserDetailsUseCase
import com.tengyeekong.githubusers.domain.usecase.user.UpdateUserNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val updateUserNoteUseCase: UpdateUserNoteUseCase
) : ViewModel() {

    /**
     * Get user profile
     * @param username value to retrieve user details
     */
    fun getUserDetails(username: String) = getUserDetailsUseCase(username)

    /**
     * Update query value and get new user list
     * @param username value to retrieve user details
     * @param note value to store as note for the user
     */
    fun updateUserNote(username: String, note: String) = viewModelScope.launch {
        updateUserNoteUseCase(Pair(username, note)).collect()
    }
}
