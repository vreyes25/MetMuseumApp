package com.project.museumapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.museumapp.adapter.DepartmentAdapter
import com.project.museumapp.viewmodel.DepartmentViewModel


class HomeFragment : Fragment() {
    private lateinit var departmentViewModel : DepartmentViewModel
    private lateinit var departmentAdapter: DepartmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        departmentViewModel = ViewModelProviders.of(this).get(DepartmentViewModel::class.java)
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

                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                }
            })
        })
    }

    override fun onResume() {
        super.onResume()
        departmentViewModel = ViewModelProviders.of(this).get(DepartmentViewModel::class.java)
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
                }
            })
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}