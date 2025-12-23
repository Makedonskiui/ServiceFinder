package com.example.servicefinder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(orderId: String) {
    var messages by remember { mutableStateOf(emptyList<ChatMessage>()) }
    var newMessage by remember { mutableStateOf("") }

    // Демо-сообщения
    LaunchedEffect(Unit) {
        messages = listOf(
            ChatMessage("Привет! Мне нужен ремонт ноутбука", false, System.currentTimeMillis() - 60000),
            ChatMessage("Здравствуйте! Могу осмотреть ваш ноутбук завтра", true, System.currentTimeMillis() - 30000),
            ChatMessage("Отлично, я по адресу ул. Ленина, 15", false, System.currentTimeMillis() - 10000)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Чат по заказу #$orderId") },
                navigationIcon = {
                    IconButton(onClick = { /* Назад */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    MessageItem(message = message)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    placeholder = { Text("Введите сообщение...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    onClick = {
                        if (newMessage.isNotBlank()) {
                            val newMsg = ChatMessage(
                                content = newMessage,
                                isCurrentUser = true,
                                timestamp = System.currentTimeMillis()
                            )
                            messages = messages + newMsg
                            newMessage = ""
                        }
                    },
                    containerColor = Color(0xFF2A5CAA)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Отправить", tint = Color.White)
                }
            }
        }
    }
}

data class ChatMessage(
    val content: String,
    val isCurrentUser: Boolean,
    val timestamp: Long
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageItem(message: ChatMessage) {
    val backgroundColor = if (message.isCurrentUser) Color(0xFF2A5CAA) else Color(0xFFF0F0F0)
    val textColor = if (message.isCurrentUser) Color.White else Color.Black

    Row(
        horizontalArrangement = if (message.isCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                    color = if (message.isCurrentUser) Color(0xCCFFFFFF) else Color.Gray,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}