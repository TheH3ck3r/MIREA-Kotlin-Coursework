package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors
import com.example.jobnechaev.ui.components.AppTopBar
import com.example.jobnechaev.ui.components.AppDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailScreen(
    vacancy: Vacancy,
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onNavigateToFavorites: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppDrawer(
        drawerState = drawerState,
        isDarkTheme = isDarkTheme,
        onThemeToggle = onThemeToggle,
        onNavigateToFavorites = onNavigateToFavorites,
        onLogout = onLogout
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Детали вакансии",
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
                // Заголовок и компания
                Text(
                    text = vacancy.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = AppColors.TextPrimary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = vacancy.company,
                        style = MaterialTheme.typography.titleMedium,
                        color = AppColors.TextSecondary
                    )
                    
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Удалить из избранного" else "Добавить в избранное",
                            tint = if (isFavorite) AppColors.Primary else AppColors.TextPrimary
                        )
                    }
                }

                // Основная информация
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Item
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoRow("Локация", vacancy.location)
                        InfoRow("Уровень", vacancy.level)
                        InfoRow("Зарплата", vacancy.salary)
                    }
                }

                // Описание
                Section(
                    title = "Описание",
                    content = vacancy.description
                )

                // Требования
                Section(
                    title = "Требования",
                    content = vacancy.requirements.joinToString("\n• ", "• ")
                )

                // Задачи
                Section(
                    title = "Задачи",
                    content = vacancy.tasks.joinToString("\n• ", "• ")
                )

                // Кнопка отклика
                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.Background
                    )
                ) {
                    Text("Откликнуться")
                }

                // Добавляем отступ внизу для удобства прокрутки
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = AppColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = AppColors.TextPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Section(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Item
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.TextPrimary
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextSecondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 