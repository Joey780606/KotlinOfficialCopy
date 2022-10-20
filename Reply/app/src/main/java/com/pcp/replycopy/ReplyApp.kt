package com.pcp.replycopy

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.pcp.replycopy.ui.utils.DevicePosture
import com.pcp.replycopy.ui.utils.ReplyContentType
import com.pcp.replycopy.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
    windowSize: WindowSizeClass,
    foldingDevicePosture: DevicePosture,    //在 WindowStateUtils.kt
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long, ReplyContentType) -> Unit = { _, _ -> }
) {
    /*
     * 這幫我們依 Window size和 device的折疊狀況,選擇navigation和內容的型態
     *
     * 如果是折疊設備,若在 BookPosture 只有一半的情況,我們會避免內容在折縫(crease)/中心(hinge)
     */
    val navigationType: ReplyNavigationType
    val contentType: ReplyContentType

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ReplyContentType.DUAL_PANE
            } else {
                ReplyContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                ReplyNavigationType.NAVIGATION_RAIL
            } else {
                ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ReplyContentType.DUAL_PANE
        }
        else -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.SINGLE_PANE
        }
    }

}