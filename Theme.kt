package com.jarvis.os.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.jarvis.os.data.AppSettings
import com.jarvis.os.data.ThemeMode

fun parseHex(hex: String, fallback: Color): Color =
    runCatching { Color(android.graphics.Color.parseColor(hex.trim())) }.getOrDefault(fallback)

@Composable
fun JarvisTheme(s: AppSettings, content: @Composable () -> Unit) {
    val dark = when (s.themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.AUTO -> isSystemInDarkTheme()
    }
    val primary = parseHex(s.primaryHex, Color(0xFF00E5FF))
    val secondary = parseHex(s.secondaryHex, Color(0xFF7C4DFF))
    val accent = parseHex(s.accentHex, Color(0xFFFF4081))
    val scheme = if (dark) darkColorScheme(
        primary = primary, secondary = secondary, tertiary = accent,
        background = Color(0xFF0B1020), surface = Color(0xFF121A30),
        onPrimary = Color.Black, onBackground = Color(0xFFE6EEFF), onSurface = Color(0xFFE6EEFF),
    ) else lightColorScheme(primary = primary, secondary = secondary, tertiary = accent)
    val d = LocalDensity.current
    CompositionLocalProvider(LocalDensity provides Density(d.density, d.fontScale * s.textScale)) {
        MaterialTheme(colorScheme = scheme, content = content)
    }
}
