package com.example.jobnechaev

import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.jobnechaev.data.model.Application
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.ui.screens.ApplicationScreen
import com.example.jobnechaev.ui.screens.LoginScreen
import com.example.jobnechaev.ui.screens.VacanciesScreen
import com.example.jobnechaev.ui.screens.VacancyDetailScreen
import com.example.jobnechaev.ui.theme.JobNechaevTheme
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Screen : Parcelable {
    object Login : Screen()
    object VacanciesList : Screen()
    @Parcelize
    data class VacancyDetail(val vacancy: Vacancy) : Screen()
    @Parcelize
    data class Application(val vacancy: Vacancy) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobNechaevTheme {
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
                                }
                            )
                        }
                        is Screen.VacanciesList -> {
                            VacanciesScreen(
                                onVacancyClick = { vacancy ->
                                    currentScreen = Screen.VacancyDetail(vacancy)
                                }
                            )
                        }
                        is Screen.VacancyDetail -> {
                            VacancyDetailScreen(
                                vacancy = screen.vacancy,
                                onBackClick = {
                                    currentScreen = Screen.VacanciesList
                                },
                                onApplyClick = {
                                    currentScreen = Screen.Application(screen.vacancy)
                                }
                            )
                        }
                        is Screen.Application -> {
                            ApplicationScreen(
                                onBackClick = {
                                    currentScreen = Screen.VacancyDetail(screen.vacancy)
                                },
                                onSubmit = { application ->
                                    // Здесь будет отправка заявки на бэкенд
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Заявка отправлена",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = Screen.VacanciesList
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 