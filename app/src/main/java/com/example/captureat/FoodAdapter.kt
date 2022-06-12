package com.example.captureat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class FoodAdapter(
    val foodList: ArrayList<FoodModel.Food>
): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
//        holder.foodImage.setImageResource(food.image_link)
        holder.foodName.text = food.title
        holder.ingredients.text = food.instructions
    }

    override fun getItemCount() = foodList.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val foodImage = view.findViewById<CircleImageView>(R.id.food_pict)
        val foodName = view.findViewById<TextView>(R.id.food_name)
        val ingredients = view.findViewById<TextView>(R.id.food_ingredients)
    }

    public fun setData(data: List<FoodModel.Food>) {
        foodList.clear()
        foodList.addAll(data)
        notifyDataSetChanged()
    }
}