package com.example.careium.core.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.careium.R

internal class GridViewAdapter(
    private val context: Context,
    private val numberImage: ArrayList<Bitmap>

) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    override fun getCount(): Int {
        return numberImage.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var ctView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (ctView == null) {
            ctView = layoutInflater!!.inflate(R.layout.layout_dish_image_item, null)
        }
        imageView = ctView!!.findViewById(R.id.imageView)
        imageView.setImageBitmap(numberImage[position])
        return ctView
    }
}