package com.example.careium.core.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careium.R
import com.example.careium.core.adapters.CalenderAdapter.ViewHolders

class CalenderAdapter(
    private val context: Context?,
    private val list: ArrayList<Int> = ArrayList()
) : RecyclerView.Adapter<ViewHolders>() {
    class ViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayNum: TextView = itemView.findViewById(R.id.day_num)
       // var day: TextView = itemView.findViewById(R.id.day)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_calender_item, parent, false)
        return ViewHolders(v)
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        val calender = list[position]
        holder.dayNum.text = calender.toString()
        //holder.day.text = calender.day
    }

    override fun getItemCount(): Int {
        return list.size
    }
}