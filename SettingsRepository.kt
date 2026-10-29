package com.jarvis.os.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.store: DataStore<Preferences> by preferencesDataStore("jarvis_settings")

class SettingsRepository(private val context: Context) {
    private object K {
        val name = stringPreferencesKey("assistantName")
        val wake = stringPreferencesKey("wakeName")
        val display = stringPreferencesKey("displayName")
        val greeting = stringPreferencesKey("greeting")
        val start = stringPreferencesKey("startMessage")
        val theme = stringPreferencesKey("themeMode")
        val primary = stringPreferencesKey("primaryHex")
        val secondary = stringPreferencesKey("secondaryHex")
        val accent = stringPreferencesKey("accentHex")
        val radius = intPreferencesKey("cornerRadiusDp")
        val scale = floatPreferencesKey("textScale")
        val compact = booleanPreferencesKey("compact")
        val tone = stringPreferencesKey("tone")
        val instr = stringPreferencesKey("customInstructions")
    }

    val settings: Flow<AppSettings> = context.store.data.map { p ->
        val d = AppSettings()
        AppSettings(
            assistantName = p[K.name] ?: d.assistantName,
            wakeName = p[K.wake] ?: d.wakeName,
            displayName = p[K.display] ?: d.displayName,
            greeting = p[K.greeting] ?: d.greeting,
            startMessage = p[K.start] ?: d.startMessage,
            themeMode = enumOr(p[K.theme], d.themeMode),
            primaryHex = p[K.primary] ?: d.primaryHex,
            secondaryHex = p[K.secondary] ?: d.secondaryHex,
            accentHex = p[K.accent] ?: d.accentHex,
            cornerRadiusDp = p[K.radius] ?: d.cornerRadiusDp,
            textScale = p[K.scale] ?: d.textScale,
            compact = p[K.compact] ?: d.compact,
            tone = enumOr(p[K.tone], d.tone),
            customInstructions = p[K.instr] ?: d.customInstructions,
        )
    }

    suspend fun update(transform: (AppSettings) -> AppSettings) {
        context.store.edit { p ->
            val cur = transform(readFrom(p))
            p[K.name] = cur.assistantName; p[K.wake] = cur.wakeName
            p[K.display] = cur.displayName; p[K.greeting] = cur.greeting
            p[K.start] = cur.startMessage; p[K.theme] = cur.themeMode.name
            p[K.primary] = cur.primaryHex; p[K.secondary] = cur.secondaryHex
            p[K.accent] = cur.accentHex; p[K.radius] = cur.cornerRadiusDp
            p[K.scale] = cur.textScale; p[K.compact] = cur.compact
            p[K.tone] = cur.tone.name; p[K.instr] = cur.customInstructions
        }
    }

    suspend fun reset() { context.store.edit { it.clear() } }

    private fun readFrom(p: Preferences): AppSettings {
        val d = AppSettings()
        return AppSettings(
            p[K.name] ?: d.assistantName, p[K.wake] ?: d.wakeName,
            p[K.display] ?: d.displayName, p[K.greeting] ?: d.greeting,
            p[K.start] ?: d.startMessage, enumOr(p[K.theme], d.themeMode),
            p[K.primary] ?: d.primaryHex, p[K.secondary] ?: d.secondaryHex,
            p[K.accent] ?: d.accentHex, p[K.radius] ?: d.cornerRadiusDp,
            p[K.scale] ?: d.textScale, p[K.compact] ?: d.compact,
            enumOr(p[K.tone], d.tone), p[K.instr] ?: d.customInstructions,
        )
    }

    private inline fun <reified E : Enum<E>> enumOr(v: String?, def: E): E =
        runCatching { enumValueOf<E>(v ?: "") }.getOrDefault(def)
}
