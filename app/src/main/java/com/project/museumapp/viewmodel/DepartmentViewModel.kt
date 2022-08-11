package com.project.museumapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.project.museumapp.utils.RetrofitRepository
import kotlinx.coroutines.Dispatchers

class DepartmentViewModel : ViewModel() {
    private val retrofitRepository : RetrofitRepository = RetrofitRepository()

    val data = liveData(Dispatchers.IO) {
        val departmentInfo = retrofitRepository.getData()
        emit(departmentInfo)
    }
}