package com.jarvis.os

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jarvis.os.ui.*

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val s by vm.settings.collectAsStateWithLifecycle()
            val msgs by vm.messages.collectAsStateWithLifecycle()
            var showSettings by remember { mutableStateOf(false) }
            JarvisTheme(s) {
                if (showSettings) {
                    SettingsScreen(s, vm::update, vm::resetSettings) { showSettings = false }
                } else {
                    ChatScreen(s, msgs, vm::send) { showSettings = true }
                }
            }
        }
    }
}
