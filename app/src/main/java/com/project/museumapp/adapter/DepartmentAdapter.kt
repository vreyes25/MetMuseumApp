package com.project.museumapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.museumapp.R
import com.project.museumapp.model.Department

class DepartmentAdapter(private val departments : List<Department>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var recyclerListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        recyclerListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.department_items, parent, false)
        return DepartmentHolder(v, recyclerListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DepartmentHolder -> {
                holder.bind(departments[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return departments.size
    }

    class DepartmentHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        fun bind(department: Department){
            itemView.findViewById<TextView>(R.id.departmentTitle).text = department.displayName
            itemView.findViewById<TextView>(R.id.departmentId).text = department.departmentId
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}