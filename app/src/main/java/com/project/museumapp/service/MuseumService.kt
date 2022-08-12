package com.project.museumapp.service

import com.project.museumapp.model.DepartmentData
import com.project.museumapp.model.DepartmentIDs
import com.project.museumapp.model.MuseumArts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MuseumService {
    @GET("departments")
    suspend fun getDepartmentsData(): DepartmentData

    @GET("objects/{id}")
    fun getData(@Path("id") id: Int): Call<MuseumArts>

    @GET("search")
    fun getAllDepartmentObjects(@Query("departmentId") departmentId: Int,
                                @Query("q") q: String): Call<DepartmentIDs>
}