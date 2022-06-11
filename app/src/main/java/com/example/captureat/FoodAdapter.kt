package com.example.captureat

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FoodAdapter(private val context: Activity, private val foodList: List<Food>): ArrayAdapter<Food>(context, R.layout.recipe_item, foodList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recipe_item, null)

        val foodPict: ImageView = view.findViewById(R.id.food_pict)
        val foodName: TextView = view.findViewById(R.id.food_name)
        val ingredients: TextView = view.findViewById(R.id.food_ingredients)

//        foodPict.setImageResource(foodList[position].image_link)
        foodName.text = foodList[position].title
        ingredients.text = foodList[position].cleaned_ingredients

        return view
    }
}