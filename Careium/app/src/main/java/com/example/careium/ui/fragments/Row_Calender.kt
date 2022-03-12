package com.example.careium.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.careium.R

private class myAdapter(context: Context): BaseAdapter() {
    val myContext : Context
    init {
        this.myContext = context
    }
    override fun getCount(): Int {
        return 10
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val myLayout = LayoutInflater.from(myContext).inflate(R.layout.row_dietcalender ,p2,false)
        myLayout.num_day_text.text="10"
        myLayout.num_day_text.text="10"

        return  myLayout
    }

    override fun getItem(p0: Int): Any {
        return ""
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

}