package com.example.captureat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.captureat.databinding.ActivityListRecipeBinding

class ListRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListRecipeBinding
    private lateinit var foodArray: ArrayList<FoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageID = intArrayOf(
            R.drawable.bakso
        )

        val foodNames = arrayOf(
            "Bakso"
        )

        val foodIngredients = arrayOf(
            "Pentol, Tahu goreng, Siomay, Siomay goreng, bihun, mi, kecap, saos, sambal"
        )

        foodArray = ArrayList()

        for(i in foodNames.indices) {
            val recipe = FoodItem(imageID[i], foodNames[i], foodIngredients[i])
            foodArray.add(recipe)
        }

        binding.lvRecipe.isClickable = true
        binding.lvRecipe.adapter = FoodAdapter(this, foodArray)
        binding.lvRecipe.setOnItemClickListener{ parent, view, position, id ->
            val name = foodNames[position]
            val picture = imageID[position]
            val ingredient = foodIngredients[position]

            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("foodName", name)
            intent.putExtra("foodPict", picture)
            intent.putExtra("foodIngredients", ingredient)
            startActivity(intent)
        }

    }
}