package com.project.museumapp.utils

import com.project.museumapp.model.DepartmentData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitClient {
    val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/"
    val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}

interface RetrofitService{
    @GET("departments")
    suspend fun getDepartmentsData(): DepartmentData
}