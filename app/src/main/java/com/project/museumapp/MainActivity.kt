package com.project.museumapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        setTheme(R.style.Theme_MuseumApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openMuseum(view: View) {
        val intent = Intent(this, MuseumActivity::class.java)
        startActivity(intent)
        finish()
    }
}