package com.pcp.compose.jetchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pcp.compose.jetchat.conversation.LocalBackPressedDispatcher
import com.pcp.compose.jetchat.ui.theme.JetchatTheme
import com.pcp.compose.jetchat.ui.theme.MainViewModel

/*
    Author: Joey
    Start time: 22/10/12
    Point:
      1. 重要的地方,加上 "重要:"
      2. Git一次可放5個重點, 加上: "重點:"
 */
class NavActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(
            ComposeView(this).apply {
                //consumeWindowInsets = false  //重要: 這個先不加,看有無影響
                setContent {
                    CompositionLocalProvider(
                        LocalBackPressedDispatcher provides this@NavActivity.onBackPressedDispatcher
                    ) {
                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)    //因為這個,所以需要加入 @OptIn(ExperimentalMaterial3Api::class)
                        val drawerOpen by viewModel.drawerShouldBeOpened.collectAsStateWithLifecycle() //重要: 這要 implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01"
                        // 且 collectAsStateWithLifecycle() 在 onCreate 要加入 ExperimentalLifecycleComposeApi::class
                    }

                    JetchatTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Greeting("Android")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetchatTheme {
        Greeting("Android")
    }
}