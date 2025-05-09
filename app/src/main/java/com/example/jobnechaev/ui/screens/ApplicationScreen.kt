package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.data.model.Application
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    onBackClick: () -> Unit,
    onSubmit: (Application) -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var portfolio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        TopAppBar(
            title = { Text("Заполнение заявки", color = AppColors.TextPrimary) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AppColors.TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColors.Item
            )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("ФИО", color = AppColors.TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppColors.Item,
                    focusedContainerColor = AppColors.Item,
                    unfocusedBorderColor = AppColors.TextDisabled,
                    focusedBorderColor = AppColors.Primary,
                    unfocusedTextColor = AppColors.TextPrimary,
                    focusedTextColor = AppColors.TextPrimary
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = age,
                onValueChange = { 
                    if (it.isEmpty() || it.toIntOrNull() != null) {
                        age = it
                    }
                },
                label = { Text("Возраст", color = AppColors.TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppColors.Item,
                    focusedContainerColor = AppColors.Item,
                    unfocusedBorderColor = AppColors.TextDisabled,
                    focusedBorderColor = AppColors.Primary,
                    unfocusedTextColor = AppColors.TextPrimary,
                    focusedTextColor = AppColors.TextPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Опыт
            OutlinedTextField(
                value = experience,
                onValueChange = { experience = it },
                label = { Text("Опыт", color = AppColors.TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppColors.Item,
                    focusedContainerColor = AppColors.Item,
                    unfocusedBorderColor = AppColors.TextDisabled,
                    focusedBorderColor = AppColors.Primary,
                    unfocusedTextColor = AppColors.TextPrimary,
                    focusedTextColor = AppColors.TextPrimary
                ),
                minLines = 3,
                maxLines = 5
            )

            // Портфолио
            OutlinedTextField(
                value = portfolio,
                onValueChange = { portfolio = it },
                label = { Text("Портфолио", color = AppColors.TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = AppColors.Item,
                    focusedContainerColor = AppColors.Item,
                    unfocusedBorderColor = AppColors.TextDisabled,
                    focusedBorderColor = AppColors.Primary,
                    unfocusedTextColor = AppColors.TextPrimary,
                    focusedTextColor = AppColors.TextPrimary
                ),
                minLines = 3,
                maxLines = 5
            )
        }

        // Submit button
        Button(
            onClick = {
                val ageInt = age.toIntOrNull() ?: 0
                onSubmit(
                    Application(
                        fullName = fullName,
                        age = ageInt,
                        experience = experience,
                        portfolio = portfolio
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Primary,
                contentColor = AppColors.Background
            ),
            enabled = fullName.isNotBlank() && age.isNotBlank() && experience.isNotBlank()
        ) {
            Text("Отправить")
        }
    }
} 