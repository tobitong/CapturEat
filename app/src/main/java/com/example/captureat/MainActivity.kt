package com.example.captureat


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captureat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val REQUEST_CODE_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenCam.setOnClickListener { openCam() }
        binding.btnOpenGallery.setOnClickListener { openGal() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GALLERY) {
            binding.ivGal.setImageURI(data?.data)
        }
    }

    private fun openCam() {
        val intent_cam = Intent(this, CameraActivity::class.java)
        startActivity(intent_cam)
    }

    private fun openGal() {
        val intent_gal = Intent(Intent.ACTION_PICK)
        intent_gal.type = "image/*"
        startActivityForResult(intent_gal, REQUEST_CODE_GALLERY)
    }
}