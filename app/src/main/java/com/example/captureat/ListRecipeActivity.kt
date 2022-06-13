package com.example.captureat

import android.content.Intent
import okhttp3.RequestBody
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.captureat.databinding.ActivityListRecipeBinding
import com.example.captureat.retrofit.ApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File

class ListRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListRecipeBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodList: RecyclerView

    private val api by lazy { ApiService.endpoint }

    private lateinit var fileUpload: File
    private lateinit var imagePath: String

    private val liveData = MutableLiveData<List<FoodModel.Food>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting the adapter
        foodList = binding.rvRecipe
        setupList()

        liveData.observe(this) {
            foodAdapter.setData(it)
        }

        //get image path from the gallery or camera
        val intent = getIntent()
        imagePath = intent.getStringExtra("Food Image").orEmpty()

        //uploading image
        lifecycleScope.launch {
            upload(imagePath).collect {
                liveData.postValue(it)
            }
        }
    }

    private fun setupList() {
        foodAdapter = FoodAdapter( object : FoodAdapter.OnAdapterListener {
            override fun onClick(recipe: FoodModel.Food) {
                startActivity(
                    Intent(this@ListRecipeActivity, RecipeActivity::class.java)
                        .putExtra("recipe", recipe)
                )
            }
        })
        foodList.adapter = foodAdapter
    }

    //method for uploading image to api
    suspend fun upload(imagePath: String?) = flow<List<FoodModel.Food>> {
        fileUpload = File(imagePath)
        val response = api.uploadImage(
            MultipartBody.Part.createFormData("image", fileUpload.name, RequestBody.create("image/jpeg".toMediaTypeOrNull(), fileUpload))
        )
        val list = response.body()!!.recipe
        emit(list)
    }.flowOn(Dispatchers.IO)
}