package com.project.museumapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.museumapp.model.Department

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val departmentId = intent.getStringExtra("departmentId")
        val departmentName = intent.getStringExtra("departmentName")

        findViewById<TextView>(R.id.txtDepartmentId).text = departmentId
        findViewById<TextView>(R.id.txtDepartment).text = departmentName
    }

    fun returnHome(view: View) {
        finish()
    }
}