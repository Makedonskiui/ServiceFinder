package com.example.servicefinder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "role_selection"
    ) {
        composable("role_selection") {
            RoleSelectionScreen(
                onCustomerSelected = { navController.navigate("customer_registration") },
                onProviderSelected = { navController.navigate("provider_registration") }
            )
        }

        composable("customer_registration") {
            CustomerRegistrationScreen(onSuccess = {
                navController.navigate("main") {
                    popUpTo("role_selection") { inclusive = true }
                }
            })
        }

        composable("provider_registration") {
            ProviderRegistrationScreen(onSuccess = {
                navController.navigate("main") {
                    popUpTo("role_selection") { inclusive = true }
                }
            })
        }

        composable("main") {
            MainScreen(
                onOrderClick = { orderId ->
                    navController.navigate("chat/${orderId}")
                }
            )
        }

        composable("chat/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            ChatScreen(orderId = orderId)
        }
    }
}