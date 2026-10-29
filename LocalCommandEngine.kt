package com.jarvis.os.engine

import com.jarvis.os.data.AppSettings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Motor local (modo básico, sem rede). Só responde o que realmente sabe fazer.
 * Etapa 3 trocará isto por uma interface AiProvider intercambiável.
 */
class LocalCommandEngine {
    fun respond(input: String, s: AppSettings, now: LocalDateTime = LocalDateTime.now()): String {
        val t = normalize(input)
        return when {
            t.isBlank() -> "Não ouvi nada. Pode repetir?"
            has(t, "que horas", "hora atual", "horas sao") ->
                "Agora são ${now.format(DateTimeFormatter.ofPattern("HH:mm"))}."
            has(t, "que dia", "data de hoje", "qual a data") ->
                "Hoje é ${now.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", Locale("pt", "BR")))}."
            has(t, "seu nome", "quem e voce", "quem voce e") ->
                "Eu sou ${s.assistantName}. Você pode mudar meu nome em Configurações."
            has(t, "ola", "oi ", "bom dia", "boa tarde", "boa noite") || t == "oi" -> s.greeting
            has(t, "abrir camera", "abre a camera") ->
                "Controle do Android chega na Etapa 4; ainda não executo essa ação."
            else -> "Ainda não sei fazer isso no modo local. Modelos de IA chegam na Etapa 3."
        }
    }

    private fun has(t: String, vararg keys: String) = keys.any { t.contains(it) }

    private fun normalize(s: String): String =
        java.text.Normalizer.normalize(s.lowercase(Locale("pt", "BR")), java.text.Normalizer.Form.NFD)
            .replace("\\p{M}+".toRegex(), "").trim()
}
