package com.jarvis.os.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jarvis.os.data.AppSettings
import com.jarvis.os.data.ThemeMode
import com.jarvis.os.data.Tone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    s: AppSettings,
    onChange: ((AppSettings) -> AppSettings) -> Unit,
    onReset: () -> Unit,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Central de Personalização") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Voltar") } },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Section("Identidade")
            Field("Nome do assistente", s.assistantName) { v -> onChange { it.copy(assistantName = v) } }
            Field("Nome de ativação", s.wakeName) { v -> onChange { it.copy(wakeName = v) } }
            Field("Nome exibido na interface", s.displayName) { v -> onChange { it.copy(displayName = v) } }
            Field("Saudação", s.greeting) { v -> onChange { it.copy(greeting = v) } }
            Field("Mensagem ao iniciar", s.startMessage) { v -> onChange { it.copy(startMessage = v) } }

            Section("Interface")
            Text("Tema")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeMode.values().forEach { m ->
                    FilterChip(selected = s.themeMode == m, onClick = { onChange { it.copy(themeMode = m) } }, label = { Text(m.name) })
                }
            }
            ColorField("Cor primária (#RRGGBB)", s.primaryHex) { v -> onChange { it.copy(primaryHex = v) } }
            ColorField("Cor secundária", s.secondaryHex) { v -> onChange { it.copy(secondaryHex = v) } }
            ColorField("Cor de destaque", s.accentHex) { v -> onChange { it.copy(accentHex = v) } }
            Text("Arredondamento dos cartões: ${s.cornerRadiusDp} dp")
            Slider(value = s.cornerRadiusDp.toFloat(), onValueChange = { v -> onChange { it.copy(cornerRadiusDp = v.toInt()) } }, valueRange = 0f..32f)
            Text("Tamanho do texto: ${"%.2f".format(s.textScale)}x")
            Slider(value = s.textScale, onValueChange = { v -> onChange { it.copy(textScale = v) } }, valueRange = 0.8f..1.4f)
            Row {
                Switch(checked = s.compact, onCheckedChange = { c -> onChange { it.copy(compact = c) } })
                Spacer(Modifier.width(8.dp)); Text("Layout compacto")
            }

            Section("Personalidade")
            Text("Tom")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Tone.values().forEach { t ->
                    FilterChip(selected = s.tone == t, onClick = { onChange { it.copy(tone = t) } }, label = { Text(t.name) })
                }
            }
            Field("Instruções personalizadas", s.customInstructions, single = false) { v -> onChange { it.copy(customInstructions = v) } }
            Text("Nota: tom e instruções serão usados pelo motor de IA a partir da Etapa 3.", style = MaterialTheme.typography.labelSmall)

            Section("Dados")
            OutlinedButton(onClick = onReset) { Text("Restaurar padrões") }
        }
    }
}

@Composable private fun Section(t: String) =
    Text(t, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

@Composable
private fun Field(label: String, value: String, single: Boolean = true, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = text, onValueChange = { text = it; onSave(it) },
        label = { Text(label) }, singleLine = single, modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ColorField(label: String, value: String, onSave: (String) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Box(Modifier.size(36.dp).background(parseHex(value, Color.Gray), RoundedCornerShape(8.dp)))
        Spacer(Modifier.width(8.dp))
        Field(label, value, onSave = onSave)
    }
}
