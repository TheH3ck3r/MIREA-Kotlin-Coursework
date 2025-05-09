package com.example.jobnechaev.data.model

data class Vacancy(
    val title: String,
    val department: String,
    val location: String,
    val level: String,
    val salary: String,
    val description: String,
    val requirements: List<String>,
    val tasks: List<String>
) 