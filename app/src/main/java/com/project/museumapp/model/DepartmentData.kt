package com.project.museumapp.model

data class DepartmentData(
    val departments: ArrayList<Department>
)

data class Department (
    val departmentId : String,
    val displayName : String
)
