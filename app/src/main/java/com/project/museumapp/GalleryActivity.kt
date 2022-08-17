package com.project.museumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    private var totalArts = 0
    private var artNum = 1
    private lateinit var title: TextView
    private lateinit var country: TextView
    private lateinit var total: TextView
    private lateinit var primaryImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        title = findViewById(R.id.txtTitle)
        country = findViewById(R.id.txtCountry)
        primaryImage = findViewById(R.id.artImage)
        total = findViewById(R.id.totalArts)

        total.text = getString(R.string.total, artNum, totalArts)
        Glide.with(this@GalleryActivity).load("https://reservarcannabis.com/static/media/loading.49db5812.gif").centerCrop().into(primaryImage)
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
        if (id.isNotEmpty()) {
            val call2: Call<MuseumArts> = retrofitService().getData(id[counter])

            call2.enqueue(object : Callback<MuseumArts> {
                override fun onResponse(call: Call<MuseumArts>, response: Response<MuseumArts>) {
                    if (response.isSuccessful) {
                        title.text = response.body()?.title
                        country.text = response.body()?.country
                        if (response.body()?.primaryImage == "") {
                            Glide.with(this@GalleryActivity).load("https://mountainholic.com/data/file/gallery/1794564802_GrmVxJz4_4f69963f2aec263bf84140cbb0de9d5dd61fc60a.png").centerCrop().into(primaryImage)
                        } else {
                            Glide.with(this@GalleryActivity).load(response.body()?.primaryImage).into(primaryImage)
                        }
                    }
                }

                override fun onFailure(call: Call<MuseumArts>, t: Throwable) {
                    Log.d("Error", "Error ${t.message}")
                }
            })
        } else {
            findViewById<TextView>(R.id.txtTitle).text = "No data available"
            findViewById<TextView>(R.id.txtCountry).text = ""
            Glide.with(this@GalleryActivity).load("https://www.itinerantnotes.com/blog-theme/images/empty.gif").centerCrop().into(findViewById(R.id.artImage))
        }
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
                    totalArts = responseBody.total
                    total.text = getString(R.string.total, artNum, totalArts)
                    if (responseBody.objectIDs != null) {
                        id = responseBody.objectIDs
                        getMyData()
                    } else {
                        id = emptyList()
                        getMyData()
                        findViewById<TextView>(R.id.totalArts).text = ""
                    }
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
            artNum--
            getMyData()
            total.text = getString(R.string.total, artNum, totalArts)
        } else {
            Toast.makeText(baseContext, getString(R.string.back), Toast.LENGTH_SHORT).show()
        }
    }

    fun nextImage(view: View) {
        if (counter < id.size - 1) {
            counter++
            artNum++
            getMyData()
            total.text = getString(R.string.total, artNum, totalArts)
        } else {
            Toast.makeText(baseContext, getString(R.string.next), Toast.LENGTH_SHORT).show()
        }
    }

    fun returnHome(view: View) {
        finish()
    }
}