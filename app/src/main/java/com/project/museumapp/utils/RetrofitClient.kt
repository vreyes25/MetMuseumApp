package com.project.museumapp.utils

import com.project.museumapp.service.MuseumService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/"
    val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MuseumService::class.java)
    }
}