package com.jarvis.os.data

enum class ThemeMode { AUTO, DARK, LIGHT }
enum class Tone { FORMAL, CASUAL, TECHNICAL, RELAXED }

/** Todas as configurações editáveis em tempo de execução (sem recompilar). */
data class AppSettings(
    val assistantName: String = "Jarvis",
    val wakeName: String = "Jarvis",
    val displayName: String = "JARVIS OS",
    val greeting: String = "Olá! Em que posso ajudar?",
    val startMessage: String = "Sistema iniciado.",
    val themeMode: ThemeMode = ThemeMode.DARK,
    val primaryHex: String = "#00E5FF",
    val secondaryHex: String = "#7C4DFF",
    val accentHex: String = "#FF4081",
    val cornerRadiusDp: Int = 16,
    val textScale: Float = 1.0f,
    val compact: Boolean = false,
    val tone: Tone = Tone.CASUAL,
    val customInstructions: String = "",
)
