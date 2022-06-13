package com.example.captureat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.captureat.databinding.RecipeItemBinding

class FoodAdapter(private val listener: OnAdapterListener): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private var foodList: List<FoodModel.Food> = emptyList()

    inner class ViewHolder(val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root)

    public fun setData(data: List<FoodModel.Food>) {
        foodList = data
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(recipe: FoodModel.Food)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        Glide.with(holder.binding.root).load(food.image_link).into(holder.binding.foodPict)
        holder.binding.foodName.text = food.title
        holder.binding.foodIngredients.text = food.instructions
        holder.itemView.setOnClickListener {
            listener.onClick(food)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RecipeItemBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )

    override fun getItemCount(): Int = foodList.size
}