package com.example.servicefinder.data.models

data class Message(
    val id: String = "",
    val orderId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)