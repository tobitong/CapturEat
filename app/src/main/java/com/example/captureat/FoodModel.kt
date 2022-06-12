package com.example.captureat

import android.graphics.Picture

data class FoodModel(
    val recipe: List<Food>
    ) {
    data class Food (
        val cleaned_ingredients: String,
        val id: Int,
        val image_link: String,
        val image_name: String,
        val instructions: String,
        val title: String
    )
}
