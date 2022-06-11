package com.example.captureat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.captureat.databinding.ActivityListRecipeBinding
import com.example.captureat.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MultipartBody
import java.io.File
import kotlin.coroutines.CoroutineContext

class ListRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListRecipeBinding
    private lateinit var foodArray: List<Food>

    private val api by lazy { ApiService.endpoint }

    private lateinit var fileUpload: File
//    private var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val imagePath = intent.getStringExtra("Food Image")

        lifecycleScope.launch {
            upload(imagePath)
        }

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

//        for(i in foodNames.indices) {
//            val recipe = FoodModel(imageID[i], foodNames[i], foodIngredients[i])
//            foodArray.add(recipe)
//        }

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

    suspend fun upload(imagePath: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            fileUpload = File(imagePath)
            val response = api.uploadImage(
                MultipartBody.Part.createFormData("image", fileUpload.name)
            )
            val listRecipes = response.body()!!.recipe
            val result = "Menampilkan resep ${listRecipes[0].title}"
            Log.d("TestActivity", result)
        }

    }
}