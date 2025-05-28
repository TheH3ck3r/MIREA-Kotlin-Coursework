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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.data.model.Application
import com.example.jobnechaev.ui.theme.AppColors
import com.example.jobnechaev.ui.components.AppTopBar
import com.example.jobnechaev.ui.components.AppDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    onBackClick: () -> Unit,
    onSubmit: (Application) -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onNavigateToFavorites: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var portfolio by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppDrawer(
        drawerState = drawerState,
        isDarkTheme = isDarkTheme,
        onThemeToggle = onThemeToggle,
        onNavigateToFavorites = onNavigateToFavorites
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Отклик на вакансию",
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    showBackButton = true,
                    onBackClick = onBackClick
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.Background)
                    .padding(paddingValues)
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

                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it },
                    label = { Text("Опыт работы", color = AppColors.TextSecondary) },
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
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (fullName.isNotEmpty() && age.isNotEmpty() && experience.isNotEmpty()) {
                            onSubmit(
                                Application(
                                    fullName = fullName,
                                    age = age.toIntOrNull() ?: 0,
                                    experience = experience,
                                    portfolio = portfolio
                                )
                            )
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
                    Text("Отправить")
                }
            }
        }
    }
} 