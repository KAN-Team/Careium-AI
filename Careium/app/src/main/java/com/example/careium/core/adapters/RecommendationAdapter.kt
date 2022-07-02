package com.example.careium.core.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careium.R
import com.example.careium.core.models.FoodCalories

class RecommendationAdapter(private val FoodArrayList: ArrayList<FoodCalories>
, private val context: Context) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_custom_food_card, parent
                , false))
    }

    override fun getItemCount(): Int {
        return FoodArrayList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food: FoodCalories = FoodArrayList[position]
        holder.foodName.text = food.getFoodName()
        holder.foodCalories.text = food.getFoodCalories().toString()
    }

    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.food_name)
        val foodCalories: TextView = itemView.findViewById(R.id.food_calories)
    }

}