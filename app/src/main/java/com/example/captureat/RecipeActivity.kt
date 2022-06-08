package com.example.captureat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captureat.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeName = intent.getStringExtra("foodName")
        val recipePict = intent.getIntExtra("foodPict", R.drawable.bakso)
        val recipeIngredients = intent.getStringExtra("foodIngredients")

        binding.recipeName.text = recipeName
        binding.recipePict.setImageResource(recipePict)
        binding.recipeIngredient.text = recipeIngredients
    }
}