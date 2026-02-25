package com.example.imagegallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagegallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: ImageGalleryAdapter

    // רישום לבחירת תמונות מרובות
    private val pickImagesLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            imageAdapter.addImages(uris)
            updateImageCount(imageAdapter.itemCount)
        }
    }

    // רישום לבקשת הרשאות
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openImagePicker()
        } else {
            Toast.makeText(this, "נדרשת הרשאה לגישה לגלריה", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageGalleryAdapter(
            onImageClick = { uri, position -> openFullScreenImage(uri, position) },
            onImageRemove = { position ->
                imageAdapter.removeImage(position)
                updateImageCount(imageAdapter.itemCount)
            }
        )

        binding.recyclerViewImages.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = imageAdapter
        }
    }

    private fun setupButtons() {
        binding.btnSelectImages.setOnClickListener {
            checkPermissionAndOpenPicker()
        }

        binding.btnClearAll.setOnClickListener {
            imageAdapter.clearAll()
            updateImageCount(0)
        }
    }

    private fun checkPermissionAndOpenPicker() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                openImagePicker()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(this, "יש לאשר גישה לגלריה כדי לבחור תמונות", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun openImagePicker() {
        pickImagesLauncher.launch("image/*")
    }

    private fun openFullScreenImage(uri: Uri, position: Int) {
        val intent = Intent(this, FullScreenImageActivity::class.java).apply {
            putExtra(FullScreenImageActivity.EXTRA_IMAGE_URI, uri.toString())
            putExtra(FullScreenImageActivity.EXTRA_POSITION, position)
        }
        startActivity(intent)
    }

    private fun updateImageCount(count: Int) {
        binding.tvImageCount.text = if (count > 0) {
            "$count תמונות נבחרו"
        } else {
            "לא נבחרו תמונות"
        }
    }
}
