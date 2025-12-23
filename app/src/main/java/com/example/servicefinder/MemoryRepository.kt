package com.example.servicefinder

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object MemoryRepository {
    // Список пользователей (сделан private для безопасности)
    private val _users = mutableStateListOf<User>()
    val users = _users

    // Текущий пользователь (сделан private)
    private var _currentUser = mutableStateOf<User?>(null)
    val currentUser = _currentUser

    // Список заказов (сделан private)
    private val _orders = mutableStateListOf<Order>()
    val orders = _orders

    // Добавить пользователя
    fun addUser(user: User) {
        _users.add(user)
        _currentUser.value = user
    }

    // Добавить заказ
    fun addOrder(order: Order) {
        _orders.add(order)
    }

    // Загрузить демо-данные
    fun loadDemoData() {
        if (_orders.isEmpty()) {
            _orders.addAll(listOf(
                Order(
                    id = "1",
                    title = "Ремонт ноутбука",
                    description = "Не включается после падения",
                    budget = "3000-5000 ₽",
                    location = "ул. Ленина, 15",
                    customerId = "user1",
                    providerId = "",
                    status = "open"
                ),
                Order(
                    id = "2",
                    title = "Уроки математики",
                    description = "Подготовка к ЕГЭ, 11 класс",
                    budget = "1500 ₽/час",
                    location = "м. Пушкинская",
                    customerId = "user2",
                    providerId = "",
                    status = "open"
                ),
                Order(
                    id = "3",
                    title = "Клининг квартиры",
                    description = "Генеральная уборка 2-комнатной квартиры",
                    budget = "4000 ₽",
                    location = "пр. Мира, 24",
                    customerId = "user3",
                    providerId = "",
                    status = "open"
                )
            ))
        }
    }
}

// Модели данных (только здесь!)
data class User(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val role: String = "customer", // "customer" или "provider"
    val inn: String = "",
    val services: List<String> = emptyList(),
    val phone: String = "",
    val address: String = "",
    val age: Int = 0
)

data class Order(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val budget: String = "",
    val location: String = "",
    val customerId: String = "",
    val providerId: String = "",
    val status: String = "open" // "open", "in_progress", "completed"
)