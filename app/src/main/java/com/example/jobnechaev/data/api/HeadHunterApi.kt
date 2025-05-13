package com.example.jobnechaev.data.api

import com.example.jobnechaev.data.model.HeadHunterResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLEncoder
import retrofit2.http.Headers

interface HeadHunterApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json; charset=utf-8"
    )
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text", encoded = true) query: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 0
    ): HeadHunterResponse
} 