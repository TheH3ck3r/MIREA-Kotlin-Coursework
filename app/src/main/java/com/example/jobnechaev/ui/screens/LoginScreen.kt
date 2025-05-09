package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Item
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Вход",
                    style = MaterialTheme.typography.headlineMedium,
                    color = AppColors.TextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { 
                        username = it
                        error = ""
                    },
                    label = { Text("Логин", color = AppColors.TextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = AppColors.BackgroundSecondary,
                        focusedContainerColor = AppColors.BackgroundSecondary,
                        unfocusedBorderColor = AppColors.TextDisabled,
                        focusedBorderColor = AppColors.Primary,
                        unfocusedTextColor = AppColors.TextPrimary,
                        focusedTextColor = AppColors.TextPrimary
                    ),
                    singleLine = true,
                    isError = error.isNotEmpty()
                )

                // Поле пароля
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        error = ""
                    },
                    label = { Text("Пароль", color = AppColors.TextSecondary) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = AppColors.BackgroundSecondary,
                        focusedContainerColor = AppColors.BackgroundSecondary,
                        unfocusedBorderColor = AppColors.TextDisabled,
                        focusedBorderColor = AppColors.Primary,
                        unfocusedTextColor = AppColors.TextPrimary,
                        focusedTextColor = AppColors.TextPrimary
                    ),
                    singleLine = true,
                    isError = error.isNotEmpty()
                )

                // Сообщение об ошибке
                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = AppColors.Primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Кнопка входа
                Button(
                    onClick = {
                        if (username.isEmpty() || password.isEmpty()) {
                            error = "Заполните все поля"
                        } else if (username == "test" && password == "test") {
                            onLoginClick(username, password)
                        } else {
                            error = "Неверный логин или пароль"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.Background
                    )
                ) {
                    Text("Войти")
                }

                // Подсказка для тестирования
                Text(
                    text = "Для входа используйте:\nЛогин: test\nПароль: test",
                    color = AppColors.TextDisabled,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
} 