package com.jarvis.os.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jarvis.os.data.AppSettings
import com.jarvis.os.data.SettingsRepository
import com.jarvis.os.engine.LocalCommandEngine
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatMessage(val fromUser: Boolean, val text: String, val engine: String? = null)

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SettingsRepository(app)
    private val engine = LocalCommandEngine()

    val settings: StateFlow<AppSettings> =
        repo.settings.stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    init {
        viewModelScope.launch {
            val s = repo.settings.first()
            _messages.value = listOf(ChatMessage(false, "${s.startMessage} ${s.greeting}", "local"))
        }
    }

    fun send(text: String) {
        if (text.isBlank()) return
        val reply = engine.respond(text, settings.value)
        _messages.value = _messages.value +
            ChatMessage(true, text) + ChatMessage(false, reply, "local")
    }

    fun update(transform: (AppSettings) -> AppSettings) {
        viewModelScope.launch { repo.update(transform) }
    }

    fun resetSettings() { viewModelScope.launch { repo.reset() } }
}
