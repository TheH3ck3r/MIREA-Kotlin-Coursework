package com.example.jobnechaev.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobnechaev.data.api.RetrofitClient
import com.example.jobnechaev.data.model.Vacancy
import com.example.jobnechaev.data.SearchHistoryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class VacanciesViewModel(application: Application) : AndroidViewModel(application) {
    private val searchHistoryManager = SearchHistoryManager(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _vacancies = MutableStateFlow<List<Vacancy>>(listOf())
    val vacancies: StateFlow<List<Vacancy>> = _vacancies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    private val _isSearchFocused = MutableStateFlow(false)
    val isSearchFocused: StateFlow<Boolean> = _isSearchFocused.asStateFlow()

    private var searchJob: Job? = null
    private var autoSearchJob: Job? = null
    private var lastQuery: String = ""

    init {
        _vacancies.value = listOf(
            Vacancy(
                title = "Название вакансии",
                company = "данные",
                location = "данные",
                level = "данные",
                salary = "данные",
                description = "ТЕКСТ ТЕКСТ ТЕКСТ ТЕКСТ ТЕКСТ",
                requirements = listOf("текст", "текст"),
                tasks = listOf("текст", "текст")
            )
        )
        loadSearchHistory()

        // Автоматический поиск через 2 секунды после открытия экрана
        viewModelScope.launch {
            delay(2000)
            if (_searchQuery.value.isEmpty() && _vacancies.value.size <= 1) {
                searchVacancies("Ну что тупим?)")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _error.value = null
        searchJob?.cancel() // Отменяем предыдущий поиск, если он был
        searchJob = viewModelScope.launch {
            delay(500) // Debounce search
            if (query.isNotEmpty()) {
                lastQuery = query
                searchVacancies(query)
            } else {
                _vacancies.value = emptyList()
            }
        }
    }

    fun setSearchFocused(focused: Boolean) {
        _isSearchFocused.value = focused
        if (focused) {
            loadSearchHistory()
        }
    }

    fun onSearchHistoryItemClick(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            searchVacancies(query)
        }
        _isSearchFocused.value = false
    }

    fun clearSearchHistory() {
        searchHistoryManager.clearSearchHistory()
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        _searchHistory.value = searchHistoryManager.getSearchHistory()
    }

    private suspend fun searchVacancies(query: String) {
        try {
            _isLoading.value = true
            _error.value = null
            val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
            val response = RetrofitClient.headHunterApi.searchVacancies(encodedQuery)
            _vacancies.value = response.items.map { hhVacancy ->
                Vacancy(
                    title = hhVacancy.name,
                    company = hhVacancy.employer.name,
                    location = hhVacancy.area.name,
                    level = "Не указан", // HeadHunter API не предоставляет это поле напрямую
                    salary = hhVacancy.salary?.let { 
                        when {
                            it.from != null && it.to != null -> "${it.from} - ${it.to} ${it.currency}"
                            it.from != null -> "от ${it.from} ${it.currency}"
                            it.to != null -> "до ${it.to} ${it.currency}"
                            else -> "Не указана"
                        }
                    } ?: "Не указана",
                    description = hhVacancy.snippet.responsibility ?: "Описание отсутствует",
                    requirements = listOf(hhVacancy.snippet.requirement ?: "Требования не указаны"),
                    tasks = listOf(hhVacancy.snippet.responsibility ?: "Обязанности не указаны")
                )
            }
            searchHistoryManager.addSearchQuery(query)
            loadSearchHistory()
        } catch (e: IOException) {
            Log.e("VacanciesViewModel", "Network error", e)
            _error.value = "Ошибка сети. Проверьте подключение к интернету"
            _vacancies.value = emptyList()
        } catch (e: Exception) {
            Log.e("VacanciesViewModel", "Error searching vacancies", e)
            _error.value = "Произошла ошибка при поиске вакансий"
            _vacancies.value = emptyList()
        } finally {
            _isLoading.value = false
        }
    }

    fun retryLastSearch() {
        if (lastQuery.isNotEmpty()) {
            viewModelScope.launch {
                searchVacancies(lastQuery)
            }
        }
    }
} 