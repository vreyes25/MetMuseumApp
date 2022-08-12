package com.project.museumapp.utils

import com.project.museumapp.service.MuseumService

class RetrofitRepository {
    var retrofitClient : MuseumService = RetrofitClient().retrofitService
    suspend fun getData() = retrofitClient.getDepartmentsData()
}