package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors
import com.example.jobnechaev.ui.components.AppTopBar
import com.example.jobnechaev.ui.components.AppDrawer
import com.example.jobnechaev.ui.components.VacancyCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteVacanciesScreen(
    favoriteVacancies: List<Vacancy>,
    onVacancyClick: (Vacancy) -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onBackClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppDrawer(
        drawerState = drawerState,
        isDarkTheme = isDarkTheme,
        onThemeToggle = onThemeToggle
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Избранные вакансии",
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
            ) {
                if (favoriteVacancies.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет избранных вакансий",
                            color = AppColors.TextSecondary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(favoriteVacancies) { vacancy ->
                            VacancyCard(
                                vacancy = vacancy,
                                onClick = { onVacancyClick(vacancy) }
                            )
                        }
                    }
                }
            }
        }
    }
} 