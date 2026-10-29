package com.jarvis.os.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.os.data.AppSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    s: AppSettings,
    messages: List<ChatMessage>,
    onSend: (String) -> Unit,
    onOpenSettings: () -> Unit,
) {
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val shape = RoundedCornerShape(s.cornerRadiusDp.dp)
    val pad = if (s.compact) 6.dp else 12.dp
    LaunchedEffect(messages.size) { if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(s.displayName) },
                actions = { TextButton(onClick = onOpenSettings) { Text("Configurações") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionContentColor = MaterialTheme.colorScheme.secondary,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { inner ->
        Column(Modifier.padding(inner).fillMaxSize().imePadding()) {
            LazyColumn(
                Modifier.weight(1f).fillMaxWidth().padding(horizontal = pad),
                state = listState, verticalArrangement = Arrangement.spacedBy(pad),
            ) {
                items(messages) { m ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (m.fromUser) Arrangement.End else Arrangement.Start) {
                        Column(
                            Modifier.widthIn(max = 300.dp).background(
                                if (m.fromUser) MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                                else MaterialTheme.colorScheme.surface, shape,
                            ).padding(pad)
                        ) {
                            Text(
                                m.text,
                                color = if (m.fromUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            )
                            m.engine?.let { Text("motor: $it", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary) }
                        }
                    }
                }
            }
            Row(Modifier.fillMaxWidth().padding(pad), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input, onValueChange = { input = it }, shape = shape,
                    modifier = Modifier.weight(1f), placeholder = { Text("Fale com ${s.assistantName}…") },
                    singleLine = true,
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = { onSend(input); input = "" }, shape = shape) { Text("Enviar") }
            }
        }
    }
}
