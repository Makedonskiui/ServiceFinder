package com.example.servicefinder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@Composable
fun CustomerRegistrationScreen(onRegistrationSuccess: () -> Unit) {
    val viewModel: RegistrationViewModel = viewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Регистрация заказчика", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.fullName,
            onValueChange = { viewModel.fullName = it },
            label = { Text("ФИО") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.age,
            onValueChange = {
                if (it.all { char -> char.isDigit() } || it.isEmpty())
                    viewModel.age = it
            },
            label = { Text("Возраст") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.phone,
            onValueChange = { viewModel.phone = it },
            label = { Text("Телефон") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.address,
            onValueChange = { viewModel.address = it },
            label = { Text("Адрес проживания") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.errorMessage.isNotEmpty()) {
            Text(text = viewModel.errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { viewModel.registerCustomer(onRegistrationSuccess) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading &&
                    viewModel.email.contains("@") &&
                    viewModel.password.length >= 6 &&
                    viewModel.fullName.isNotBlank()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Зарегистрироваться")
            }
        }
    }
}

class RegistrationViewModel : ViewModel() {
    var fullName by mutableStateOf("")
    var age by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun registerCustomer(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                // Регистрация в Supabase Auth
                val result = SupabaseClient.auth.signUpWith(
                    email = email,
                    password = password,
                    data = mapOf("full_name" to fullName)
                )

                // Сохранение дополнительных данных в профиль
                if (result.user != null) {
                    val profileData = mapOf(
                        "id" to result.user.id,
                        "email" to email,
                        "role" to "customer",
                        "full_name" to fullName,
                        "age" to (age.toIntOrNull() ?: 18).toString(),
                        "phone" to phone,
                        "address" to address
                    )

                    SupabaseClient.database.from("profiles").insert(profileData)
                }

                onSuccess()
            } catch (e: Exception) {
                errorMessage = "Ошибка: ${e.message ?: "Неизвестная ошибка"}"
            } finally {
                isLoading = false
            }
        }
    }
}