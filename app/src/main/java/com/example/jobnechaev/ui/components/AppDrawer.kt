package com.example.jobnechaev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobnechaev.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    drawerState: DrawerState,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onNavigateToFavorites: () -> Unit = {},
    onLogout: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = AppColors.Item
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Меню",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = AppColors.TextPrimary
                )
                Divider(color = AppColors.TextDisabled)
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Избранные вакансии",
                            tint = AppColors.TextPrimary
                        )
                    },
                    label = {
                        Text(
                            "Избранные вакансии",
                            color = AppColors.TextPrimary
                        )
                    },
                    selected = false,
                    onClick = onNavigateToFavorites,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = AppColors.Item,
                        selectedContainerColor = AppColors.Primary.copy(alpha = 0.1f)
                    )
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Сменить тему",
                            tint = AppColors.TextPrimary
                        )
                    },
                    label = {
                        Text(
                            if (isDarkTheme) "Светлая тема" else "Темная тема",
                            color = AppColors.TextPrimary
                        )
                    },
                    selected = false,
                    onClick = onThemeToggle,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = AppColors.Item,
                        selectedContainerColor = AppColors.Primary.copy(alpha = 0.1f)
                    )
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Выйти из аккаунта",
                            tint = AppColors.TextPrimary
                        )
                    },
                    label = {
                        Text(
                            "Выйти из аккаунта",
                            color = AppColors.TextPrimary
                        )
                    },
                    selected = false,
                    onClick = onLogout,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = AppColors.Item,
                        selectedContainerColor = AppColors.Primary.copy(alpha = 0.1f)
                    )
                )
            }
        }
    ) {
        content()
    }
} 