package com.example.servicefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // УБРАЛ ServiceFinderTheme - он не создан в проекте
            RoleSelectionScreen()
        }
    }
}

@Composable
fun RoleSelectionScreen() {
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
                onClick = { selectedRole = "customer" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleButton(
                title = "Я поставщик",
                description = "Предоставляю услуги клиентам",
                isSelected = selectedRole == "provider",
                onClick = { selectedRole = "provider" }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* TODO: Переход к регистрации */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedRole.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A5CAA))
            ) {
                Text("Продолжить", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun RoleButton(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) Color(0x1A2A5CAA) else Color.Transparent,
            contentColor = Color(0xFF2A5CAA)
        ),
        border = ButtonDefaults.outlinedButtonBorder // ИСПРАВЛЕНО: используем стандартную границу
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = description, color = Color.Gray, fontSize = 14.sp)
        }
    }
}