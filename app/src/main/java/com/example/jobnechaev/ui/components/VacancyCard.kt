package com.example.jobnechaev.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyCard(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                text = vacancy.title,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.TextPrimary
            )
            
            Text(
                text = vacancy.company,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextSecondary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = vacancy.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondary
                )
                
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondary
                )
                
                Text(
                    text = vacancy.level,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondary
                )
            }
            
            Text(
                text = vacancy.salary,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Primary
            )
        }
    }
} 