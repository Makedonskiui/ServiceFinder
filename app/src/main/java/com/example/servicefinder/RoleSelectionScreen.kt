package com.example.servicefinder

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoleSelectionScreen(
    onCustomerSelected: () -> Unit = {},
    onProviderSelected: () -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "ServiceFinder",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A5CAA)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "Выберите вашу роль", fontSize = 20.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(32.dp))

            RoleButton(
                title = "Я заказчик",
                description = "Ищу исполнителя для услуги",
                isSelected = selectedRole == "customer",
                onClick = {
                    selectedRole = "customer"
                    onCustomerSelected()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleButton(
                title = "Я поставщик",
                description = "Предоставляю услуги клиентам",
                isSelected = selectedRole == "provider",
                onClick = {
                    selectedRole = "provider"
                    onProviderSelected()
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* Кнопка активируется через колбэки */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A5CAA))
            ) {
                Text("Продолжить", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}