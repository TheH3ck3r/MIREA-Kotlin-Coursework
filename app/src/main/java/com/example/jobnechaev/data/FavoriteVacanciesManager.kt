package com.example.jobnechaev.data

import android.content.Context
import android.content.SharedPreferences
import com.example.jobnechaev.data.model.Vacancy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteVacanciesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addVacancy(vacancy: Vacancy) {
        val favorites = getFavoriteVacancies().toMutableList()
        
        // Проверяем, нет ли уже такой вакансии в избранном
        if (!favorites.any { it.title == vacancy.title && it.company == vacancy.company }) {
            favorites.add(vacancy)
            saveFavoriteVacancies(favorites)
        }
    }

    fun removeVacancy(vacancy: Vacancy) {
        val favorites = getFavoriteVacancies().toMutableList()
        favorites.removeAll { it.title == vacancy.title && it.company == vacancy.company }
        saveFavoriteVacancies(favorites)
    }

    fun getFavoriteVacancies(): List<Vacancy> {
        val json = prefs.getString(KEY_FAVORITE_VACANCIES, null) ?: return emptyList()
        val type = object : TypeToken<List<Vacancy>>() {}.type
        return gson.fromJson(json, type)
    }

    fun isFavorite(vacancy: Vacancy): Boolean {
        return getFavoriteVacancies().any { it.title == vacancy.title && it.company == vacancy.company }
    }

    private fun saveFavoriteVacancies(vacancies: List<Vacancy>) {
        val json = gson.toJson(vacancies)
        prefs.edit().putString(KEY_FAVORITE_VACANCIES, json).apply()
    }

    companion object {
        private const val PREFS_NAME = "favorite_vacancies_prefs"
        private const val KEY_FAVORITE_VACANCIES = "favorite_vacancies"
    }
} 