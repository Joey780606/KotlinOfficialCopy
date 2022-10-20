package com.pcp.compose.jetchat.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _drawerShouldBeOpened = MutableStateFlow(false)
    val drawerShouldBeOpened: StateFlow<Boolean> = _drawerShouldBeOpened

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }
}