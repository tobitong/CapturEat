package com.example.captureat


import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.example.captureat.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val REQUEST_CODE_GALLERY = 1
    private val REQUEST_CODE_CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting button to open camera and gallery
        binding.btnOpenCam.setOnClickListener { openCam() }
        binding.btnOpenGallery.setOnClickListener {
            openGal()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //checking if the result is not null
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_GALLERY) {
                val temp = uriToFile(data?.data!!, this)
                openListRecipe(temp.path)
            }else if(requestCode==REQUEST_CODE_CAMERA) {
                val path = data?.getStringExtra("Image File").orEmpty()
                openListRecipe(path)
            }
        }

    }

    private fun openCam() {
        val intent_cam = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent_cam, REQUEST_CODE_CAMERA)
    }

    private fun openGal() {
        val intent_gal = Intent(Intent.ACTION_PICK)
        intent_gal.type = "image/*"
        startActivityForResult(intent_gal, REQUEST_CODE_GALLERY)
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val file = File.createTempFile("FoodImage", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return file
    }

    private fun openListRecipe(path: String) {
        val intent_next = Intent(this, ListRecipeActivity::class.java)
        intent_next.putExtra("Food Image", path)
        startActivity(intent_next)
    }
}