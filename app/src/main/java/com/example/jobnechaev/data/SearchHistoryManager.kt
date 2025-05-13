package com.example.jobnechaev.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addSearchQuery(query: String) {
        val history = getSearchHistory().toMutableList()
        
        // Удаляем запрос, если он уже есть (чтобы переместить его в начало)
        history.remove(query)
        
        // Добавляем новый запрос в начало списка
        history.add(0, query)
        
        // Ограничиваем размер истории до 10 элементов
        while (history.size > 10) {
            history.removeAt(history.size - 1)
        }
        
        // Сохраняем обновленную историю
        saveSearchHistory(history)
    }

    fun getSearchHistory(): List<String> {
        val json = prefs.getString(KEY_SEARCH_HISTORY, null) ?: return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearSearchHistory() {
        prefs.edit().remove(KEY_SEARCH_HISTORY).apply()
    }

    private fun saveSearchHistory(history: List<String>) {
        val json = gson.toJson(history)
        prefs.edit().putString(KEY_SEARCH_HISTORY, json).apply()
    }

    companion object {
        private const val PREFS_NAME = "search_history_prefs"
        private const val KEY_SEARCH_HISTORY = "search_history"
    }
} 