package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
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
                        text = "Регистрация",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AppColors.TextPrimary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { 
                            email = it
                            error = ""
                        },
                        label = { Text("Email", color = AppColors.TextSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = AppColors.BackgroundSecondary,
                            focusedContainerColor = AppColors.BackgroundSecondary,
                            unfocusedBorderColor = AppColors.TextDisabled,
                            focusedBorderColor = AppColors.Primary,
                            unfocusedTextColor = AppColors.TextPrimary,
                            focusedTextColor = AppColors.TextPrimary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        isError = error.isNotEmpty()
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

                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = AppColors.Primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Button(
                        onClick = {
                            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                                error = "Заполните все поля"
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                error = "Введите корректный email"
                            } else if (password.length < 6) {
                                error = "Пароль должен содержать минимум 6 символов"
                            } else {
                                onRegisterClick(email, username, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary,
                            contentColor = AppColors.Background
                        )
                    ) {
                        Text("Зарегистрироваться")
                    }

                    TextButton(
                        onClick = onBackClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Уже есть аккаунт? Войти",
                            color = AppColors.Primary
                        )
                    }
                }
            }
        }
    }
} 