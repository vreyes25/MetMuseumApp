package com.project.museumapp.utils

class RetrofitRepository {
    var retrofitClient : RetrofitService = RetrofitClient().retrofitService
    suspend fun getData() = retrofitClient.getDepartmentsData()
}