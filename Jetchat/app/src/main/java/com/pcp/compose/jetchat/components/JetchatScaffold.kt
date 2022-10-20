package com.pcp.compose.jetchat.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.pcp.compose.jetchat.ui.theme.JetchatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetchatDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    //重要: DrawerValue 是 material3 的功能,實驗性,前面需加 @OptIn(ExperimentalMaterial3Api::class)
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    content: @Composable () -> Unit
) {
    JetchatTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                JetchatDrawerContent(
                    onProfileClicked = onProfileClicked,
                    onChatClicked = onChatClicked
                )
            },
            content = content
        )
    }
}