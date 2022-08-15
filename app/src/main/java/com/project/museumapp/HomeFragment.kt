package com.project.museumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.museumapp.adapter.DepartmentAdapter
import com.project.museumapp.viewmodel.DepartmentViewModel
import java.text.FieldPosition


class HomeFragment : Fragment() {
    private lateinit var departmentViewModel : DepartmentViewModel
    private lateinit var departmentAdapter: DepartmentAdapter
    private lateinit var sp: SharedPref
    var index: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showData()
    }

    override fun onResume() {
        super.onResume()
        showData()
        if (index != 0)
            view?.findViewById<RecyclerView>(R.id.rvDepartments)?.scrollToPosition(index)
    }

    override fun onDestroy() {
        super.onDestroy()
        index = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        sp = SharedPref(view.context)

        if (sp.loadNightModeState() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        return  view
    }

    private fun showData() {
        departmentViewModel = ViewModelProviders.of(this)[DepartmentViewModel::class.java]
        departmentViewModel.data.observe(this, Observer { data ->
            departmentAdapter = DepartmentAdapter(departments = data.departments)
            view?.findViewById<RecyclerView>(R.id.rvDepartments)?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            view?.findViewById<RecyclerView>(R.id.rvDepartments)?.adapter = departmentAdapter
            departmentAdapter.setOnItemClickListener(object : DepartmentAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, GalleryActivity::class.java)
                    intent.putExtra("departmentId", data.departments[position].departmentId)
                    intent.putExtra("departmentName", data.departments[position].displayName)
                    startActivity(intent)
                    index = position
                    i("Position", "Position: ${position.toString()}, Index: ${index.toString()}")
                }
            })
        })
    }
}