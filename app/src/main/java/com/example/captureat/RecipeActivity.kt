package com.example.captureat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.captureat.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    private val recipe by lazy { intent.getSerializableExtra("recipe") as FoodModel.Food }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.recipeInstructions.setBackgroundColor(Color.WHITE)
//        binding.recipeIngredient.setBackgroundColor(Color.WHITE)

        Glide.with(baseContext).load(recipe.image_link).into(binding.recipePict)
        binding.recipeName.text = recipe.title
        binding.recipeIngredient.text = splitter(recipe.cleaned_ingredients)
        binding.recipeInstructions.text = recipe.instructions
    }

    //method for parsing data
    private fun splitter(text: String): String {
        //setting delimiter
        val delim1 = "['"
        val delim2 = "']"
        val delim3 = "', '"

        val list = text.split(delim1, delim2, delim3)

        var result = ""
        for (l in list) {
            result += "${l} \n"
        }

        return result
    }
}