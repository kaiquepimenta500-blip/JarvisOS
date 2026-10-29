package com.jarvis.os

import com.jarvis.os.data.AppSettings
import com.jarvis.os.engine.LocalCommandEngine
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class LocalCommandEngineTest {
    private val e = LocalCommandEngine()
    private val s = AppSettings(assistantName = "Friday")
    private val now = LocalDateTime.of(2026, 10, 29, 14, 5)

    @Test fun hora() = assertTrue(e.respond("Que horas são?", s, now).contains("14:05"))
    @Test fun data() = assertTrue(e.respond("Qual a data de hoje", s, now).contains("2026"))
    @Test fun nome() = assertTrue(e.respond("Qual é o seu nome?", s, now).contains("Friday"))
    @Test fun desconhecido() = assertTrue(e.respond("faça um bolo", s, now).contains("Ainda não sei"))
    @Test fun vazio() = assertTrue(e.respond("  ", s, now).contains("Não ouvi"))
}
