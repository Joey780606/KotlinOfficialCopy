package com.pcp.replycopy.ui.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) } //重要: contract 需要 @OptIn(ExperimentalContracts::class)
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) } //重要: contract 需要 @OptIn(ExperimentalContracts::class)
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

// 依賴device 和 state的被 app支援的不同 navigation type
enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

// 按原文字意看,是指設備是單螢幕,或是有多螢幕(折疊手機??)
enum class ReplyContentType {
    SINGLE_PANE, DUAL_PANE
}