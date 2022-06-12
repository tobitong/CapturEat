package com.example.captureat

import android.content.Intent
import okhttp3.RequestBody
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.captureat.databinding.ActivityListRecipeBinding
import com.example.captureat.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import kotlin.coroutines.CoroutineContext

class ListRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListRecipeBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodList: RecyclerView

    private val api by lazy { ApiService.endpoint }

    private lateinit var fileUpload: File
    private lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        imagePath = intent.getStringExtra("Food Image").orEmpty()


    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            upload(imagePath)
        }
    }

    private fun setupList() {
        foodList = binding.rvRecipe
        foodAdapter = FoodAdapter(arrayListOf())
        foodList.adapter = foodAdapter
    }

    suspend fun upload(imagePath: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            fileUpload = File(imagePath)
            val response = api.uploadImage(
                MultipartBody.Part.createFormData("image", fileUpload.name, RequestBody.create("image/jpeg".toMediaTypeOrNull(), fileUpload))
            )
            val listRecipes = response.body()!!.recipe
            val result = "Menampilkan resep ${listRecipes[0].title}"
            Log.d("TestActivity", result)
        }

    }
}