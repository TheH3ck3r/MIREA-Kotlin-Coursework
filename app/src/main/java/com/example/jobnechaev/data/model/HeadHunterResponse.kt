package com.example.jobnechaev.data.model

import com.google.gson.annotations.SerializedName

data class HeadHunterResponse(
    @SerializedName("items")
    val items: List<HeadHunterVacancy>,
    @SerializedName("found")
    val found: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("page")
    val page: Int
)

data class HeadHunterVacancy(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("employer")
    val employer: Employer,
    @SerializedName("salary")
    val salary: Salary?,
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("area")
    val area: Area
)

data class Employer(
    @SerializedName("name")
    val name: String
)

data class Salary(
    @SerializedName("from")
    val from: Int?,
    @SerializedName("to")
    val to: Int?,
    @SerializedName("currency")
    val currency: String
)

data class Snippet(
    @SerializedName("requirement")
    val requirement: String?,
    @SerializedName("responsibility")
    val responsibility: String?
)

data class Area(
    @SerializedName("name")
    val name: String
) 