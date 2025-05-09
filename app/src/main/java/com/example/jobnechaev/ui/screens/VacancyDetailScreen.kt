package com.example.jobnechaev.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailScreen(
    vacancy: Vacancy,
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        TopAppBar(
            title = { Text("Все вакансии", color = AppColors.TextPrimary) },
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
                .padding(16.dp)
        ) {
            Text(
                text = vacancy.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp, bottom = 8.dp),
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

            // Level and Salary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp, bottom = 16.dp),
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

            // Description
            Text(
                text = "Описание",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = vacancy.description,
                color = AppColors.TextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Requirements
            Text(
                text = "Требования",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            vacancy.requirements.forEach { requirement ->
                Text(
                    text = "• $requirement",
                    color = AppColors.TextSecondary,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Tasks
            Text(
                text = "Задачи",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            vacancy.tasks.forEach { task ->
                Text(
                    text = "• $task",
                    color = AppColors.TextSecondary,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
        }

        // Bottom buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Primary)
                .padding(32.dp)
        ) {
            Text(
                text = "Оставьте отклик",
                color = AppColors.Background,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Background,
                    contentColor = AppColors.TextPrimary
                )
            ) {
                Text("Откликнуться")
            }
        }
    }
} 