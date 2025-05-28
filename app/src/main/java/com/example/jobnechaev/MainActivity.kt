package com.example.jobnechaev

import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobnechaev.data.FavoriteVacanciesManager
import com.example.jobnechaev.data.model.Application
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.screens.*
import com.example.jobnechaev.ui.theme.JobNechaevTheme
import com.example.jobnechaev.ui.theme.ThemeViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Screen : Parcelable {
    object Login : Screen()
    object Register : Screen()
    object VacanciesList : Screen()
    @Parcelize
    data class Favorites(val previousScreen: Screen) : Screen()
    @Parcelize
    data class VacancyDetail(val vacancy: Vacancy) : Screen()
    @Parcelize
    data class Application(val vacancy: Vacancy) : Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var favoriteVacanciesManager: FavoriteVacanciesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteVacanciesManager = FavoriteVacanciesManager(this)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            var currentFavoriteState by remember { mutableStateOf(false) }

            JobNechaevTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var currentScreen: Screen by rememberSaveable { mutableStateOf(Screen.Login) }

                    when (val screen = currentScreen) {
                        is Screen.Login -> {
                            LoginScreen(
                                onLoginClick = { username, password ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Успешная авторизация",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = Screen.VacanciesList
                                },
                                onRegisterClick = {
                                    currentScreen = Screen.Register
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() }
                            )
                        }
                        is Screen.Register -> {
                            RegisterScreen(
                                onRegisterClick = { email, username, password ->
                                    // Здесь будет логика регистрации
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Регистрация успешна",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = Screen.Login
                                },
                                onBackClick = {
                                    currentScreen = Screen.Login
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() }
                            )
                        }
                        is Screen.VacanciesList -> {
                            VacanciesScreen(
                                onVacancyClick = { vacancy ->
                                    currentScreen = Screen.VacancyDetail(vacancy)
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() },
                                onNavigateToFavorites = {
                                    currentScreen = Screen.Favorites(screen)
                                }
                            )
                        }
                        is Screen.Favorites -> {
                            FavoriteVacanciesScreen(
                                favoriteVacancies = favoriteVacanciesManager.getFavoriteVacancies(),
                                onVacancyClick = { vacancy ->
                                    currentScreen = Screen.VacancyDetail(vacancy)
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() },
                                onBackClick = {
                                    currentScreen = screen.previousScreen
                                }
                            )
                        }
                        is Screen.VacancyDetail -> {
                            currentFavoriteState = favoriteVacanciesManager.isFavorite(screen.vacancy)
                            VacancyDetailScreen(
                                vacancy = screen.vacancy,
                                onBackClick = {
                                    currentScreen = Screen.VacanciesList
                                },
                                onApplyClick = {
                                    currentScreen = Screen.Application(screen.vacancy)
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() },
                                isFavorite = currentFavoriteState,
                                onFavoriteClick = {
                                    if (favoriteVacanciesManager.isFavorite(screen.vacancy)) {
                                        favoriteVacanciesManager.removeVacancy(screen.vacancy)
                                        currentFavoriteState = false
                                    } else {
                                        favoriteVacanciesManager.addVacancy(screen.vacancy)
                                        currentFavoriteState = true
                                    }
                                },
                                onNavigateToFavorites = {
                                    currentScreen = Screen.Favorites(screen)
                                }
                            )
                        }
                        is Screen.Application -> {
                            ApplicationScreen(
                                onBackClick = {
                                    currentScreen = Screen.VacancyDetail(screen.vacancy)
                                },
                                onSubmit = { application ->
                                    // Здесь будет логика отправки заявки
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Заявка отправлена",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = Screen.VacanciesList
                                },
                                isDarkTheme = isDarkTheme,
                                onThemeToggle = { themeViewModel.toggleTheme() },
                                onNavigateToFavorites = {
                                    currentScreen = Screen.Favorites(screen)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 