package com.example.servicefinder

sealed class Screen private constructor(val route: String) {
    data object RoleSelection : Screen("role_selection")
    data object CustomerRegistration : Screen("customer_registration")
    data object ProviderRegistration : Screen("provider_registration")
    data object Main : Screen("main")
    data object Chat : Screen("chat/{orderId}")

    fun withArgs(vararg args: String): String {
        return when(this) {
            Chat -> "chat/${args[0]}"
            else -> route
        }
    }
}