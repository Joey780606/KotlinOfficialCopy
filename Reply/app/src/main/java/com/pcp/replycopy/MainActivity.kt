package com.pcp.replycopy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi //這就需要implement androidx.compose.material3:material3-window-size-class:1.0.0-rc01
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.pcp.replycopy.ui.theme.ReplyCopyTheme
import com.pcp.replycopy.ui.utils.DevicePosture
import com.pcp.replycopy.ui.utils.isBookPosture
import com.pcp.replycopy.ui.utils.isSeparating
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*
    Author: Joey
    Start time: 22/10/12
    Point:
      1. 重要的地方,加上 "重要:"
      2. Git一次可放5個重點, 加上: "重點:"
 */

class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)  //calculateWindowSizeClass 需要
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this) //重要: 要 implementation androidx.window
            .flowWithLifecycle(this.lifecycle)  //重要: 需要 import kotlinx.coroutines.flow.SharingStarted
            .map { layoutInfo ->  //重要: 需要 import kotlinx.coroutines.flow.map
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()  //重要: 需要 import androidx.window.layout.FoldingFeature
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->    //utils > WindowStateUtils.kt
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn( //重要: 需要 import kotlinx.coroutines.flow.stateIn
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            ReplyCopyTheme {
                val windowSize = calculateWindowSizeClass(this)
                val devicePosture by devicePostureFlow.collectAsState()
                //重要: 上方需要加入 import androidx.compose.runtime.collectAsState 及 androidx.compose.runtime.getValue
                val uiState by viewModel.uiState.collectAsState()

                ReplyApp(
                    windowSize = windowSize,
                    foldingDevicePosture = devicePosture,
                    replyHomeUIState = uiState,
                    closeDetailScreen = {
                        viewModel.closeDetailScreen()  //處理程式放在 ReplyHomeViewModel
                    },
                    navigateToDetail = { emailId, pane ->
                        viewModel.setSelectedEmail(emailId, pane)
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReplyCopyTheme {
        Greeting("Android")
    }
}