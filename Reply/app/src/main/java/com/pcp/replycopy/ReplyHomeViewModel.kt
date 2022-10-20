package com.pcp.replycopy

import androidx.lifecycle.ViewModel
import com.pcp.replycopy.data.Email
import com.pcp.replycopy.ui.utils.ReplyContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReplyHomeViewModel(): ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    fun setSelectedEmail(emailId: Long, contentType: ReplyContentType) { //ReplyContentType 在 WindowStateUtils.kt
        /**
         * We only set isDetailOnlyOpen to true when it's only single pane layout
         */
        val email = uiState.value.emails.find { it.id == emailId }
        _uiState.value = _uiState.value.copy(
            selectedEmail = email,
            isDetailOnlyOpen = contentType == ReplyContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedEmail = _uiState.value.emails.first()
            )
    }
}

data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)