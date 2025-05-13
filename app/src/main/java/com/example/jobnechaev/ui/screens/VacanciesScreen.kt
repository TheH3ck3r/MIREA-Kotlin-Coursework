package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors
import com.example.jobnechaev.ui.viewmodels.VacanciesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacanciesScreen(
    viewModel: VacanciesViewModel = viewModel(),
    onVacancyClick: (Vacancy) -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val vacancies by viewModel.vacancies.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        // Запрашиваем фокус при первом отображении экрана
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        TopAppBar(
            title = { Text("Поиск вакансий", color = AppColors.TextPrimary) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColors.Item
            )
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    // Можно добавить дополнительную логику при изменении фокуса
                },
            placeholder = { Text("Поиск вакансий...", color = AppColors.TextSecondary) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = AppColors.Item,
                focusedContainerColor = AppColors.Item,
                unfocusedBorderColor = AppColors.TextDisabled,
                focusedBorderColor = AppColors.Primary,
                unfocusedTextColor = AppColors.TextPrimary,
                focusedTextColor = AppColors.TextPrimary
            ),
            leadingIcon = {
                IconButton(
                    onClick = { 
                        focusRequester.requestFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AppColors.TextSecondary
                    )
                }
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            viewModel.updateSearchQuery("")
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Очистить",
                            tint = AppColors.TextSecondary
                        )
                    }
                }
            },
            singleLine = true
        )

        if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.errorContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { viewModel.retryLastSearch() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Обновить")
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.Primary)
            }
        } else {
            if (vacancies.isEmpty() && searchQuery.isNotEmpty() && error == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ничего не найдено",
                        color = AppColors.TextSecondary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (searchQuery.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Введите текст для поиска вакансий",
                        color = AppColors.TextSecondary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Vacancies list
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(vacancies) { vacancy ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyCard(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Item
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            // Title
            Text(
                text = vacancy.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = AppColors.TextPrimary
            )

            // Company and Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Компания", color = AppColors.TextSecondary)
                    Text(
                        text = vacancy.company,
                        color = AppColors.TextDisabled
                    )
                }
                Column {
                    Text("Локация", color = AppColors.TextSecondary)
                    Text(
                        text = vacancy.location,
                        color = AppColors.TextDisabled
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Level and Salary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Уровень", color = AppColors.TextSecondary)
                    Text(
                        text = vacancy.level,
                        color = AppColors.TextDisabled
                    )
                }
                Column {
                    Text("ЗП", color = AppColors.TextSecondary)
                    Text(
                        text = vacancy.salary,
                        color = AppColors.TextDisabled
                    )
                }
            }
        }
    }
} 