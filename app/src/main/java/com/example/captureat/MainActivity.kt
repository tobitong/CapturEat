package com.example.captureat

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_open_cam).setOnClickListener {
            val intent_cam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(intent_cam.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, IMAGE_REQUEST_CODE)
            }
        }

        button = findViewById(R.id.btn_open_gallery)
        imageView = findViewById(R.id.food_image)
        button.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {
        val intent_gallery = Intent(Intent.ACTION_PICK)
        intent_gallery.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageURI(data?.data)
        }


    }
}