package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacanciesScreen(
    vacancies: List<Vacancy> = listOf(
        Vacancy(
            title = "Название вакансии",
            department = "данные",
            location = "данные",
            level = "данные",
            salary = "данные",
            description = "ТЕКСТ ТЕКСТ ТЕКСТ ТЕКСТ ТЕКСТ",
            requirements = listOf("текст", "текст"),
            tasks = listOf("текст", "текст")
        )
    ),
    onVacancyClick: (Vacancy) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        TopAppBar(
            title = { Text("Хэдэр", color = AppColors.TextPrimary) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColors.Item
            )
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Поиск", color = AppColors.TextSecondary) },
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
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = AppColors.TextSecondary
                )
            },
            singleLine = true
        )

        // Title
        Text(
            text = "Все вакансии",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary
        )

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

            // Department and Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Отдел", color = AppColors.TextSecondary)
                    Text(
                        text = vacancy.department,
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