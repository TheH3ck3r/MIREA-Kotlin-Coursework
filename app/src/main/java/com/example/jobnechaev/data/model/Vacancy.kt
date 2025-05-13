package com.example.jobnechaev.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val title: String,
    val company: String,
    val location: String,
    val level: String,
    val salary: String,
    val description: String,
    val requirements: List<String>,
    val tasks: List<String>
) : Parcelable 