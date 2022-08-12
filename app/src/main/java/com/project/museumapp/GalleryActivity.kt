package com.project.museumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.project.museumapp.model.DepartmentIDs
import com.project.museumapp.model.MuseumArts
import com.project.museumapp.service.MuseumService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GalleryActivity : AppCompatActivity() {

    private var counter = 0
    private lateinit var id : List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val departmentId = intent.getStringExtra("departmentId")
        val departmentName = intent.getStringExtra("departmentName")

        findViewById<TextView>(R.id.txtDepartmentId).text = departmentId
        findViewById<TextView>(R.id.txtDepartment).text = departmentName

        getObjectIDs(Integer.parseInt(departmentId))
    }

    private fun retrofitService(): MuseumService {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1/")
            .build()

        return retrofitBuilder.create(MuseumService::class.java)
    }

    private fun getMyData() {
        val call2: Call<MuseumArts> = retrofitService().getData(id[counter])

        call2.enqueue(object : Callback<MuseumArts> {
            override fun onResponse(call: Call<MuseumArts>, response: Response<MuseumArts>) {
                if (response.isSuccessful) {
                    findViewById<TextView>(R.id.txtTitle).text = response.body()?.title
                    findViewById<TextView>(R.id.txtCountry).text = response.body()?.country
                    if (response.body()?.primaryImage == "") {
                        Glide.with(this@GalleryActivity).load("https://t3.ftcdn.net/jpg/04/34/72/82/360_F_434728286_OWQQvAFoXZLdGHlObozsolNeuSxhpr84.jpg").centerCrop().into(findViewById(R.id.artImage))
                    } else {
                        Glide.with(this@GalleryActivity).load(response.body()?.primaryImage).into(findViewById(R.id.artImage))
                    }
                }
            }

            override fun onFailure(call: Call<MuseumArts>, t: Throwable) {
                Log.d("Error", "Error ${t.message}")
            }
        })
    }

    private fun getObjectIDs(departmentId: Int) {
        val call = retrofitService().getAllDepartmentObjects(departmentId, "cat")

        call.enqueue(object : Callback<DepartmentIDs?> {
            override fun onResponse(
                call: Call<DepartmentIDs?>,
                response: Response<DepartmentIDs?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    id = responseBody.objectIDs
                    Log.i("ObjectID's", id.toString())
                    getMyData()
                }
            }

            override fun onFailure(call: Call<DepartmentIDs?>, t: Throwable) {
                Log.d("Error", "ID's can't be reached ${t.message}")
            }
        })
    }

    fun backImage(view: View) {
        if (counter != 0) {
            counter--
            getMyData()
        }
    }

    fun nextImage(view: View) {
        if (counter < id.size - 1) {
            counter++
            getMyData()
        }
    }

    fun returnHome(view: View) {
        finish()
    }
}