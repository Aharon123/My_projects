package com.example.imagegallery

import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagegallery.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var binding: ActivityFullScreenImageBinding
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private var scaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // הסתרת סרגל הכותרת למסך מלא
        supportActionBar?.hide()

        val uriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        val position = intent.getIntExtra(EXTRA_POSITION, 0)

        if (uriString != null) {
            loadImage(Uri.parse(uriString), position)
        }

        setupGestures()
        setupBackButton()
    }

    private fun loadImage(uri: Uri, position: Int) {
        binding.tvImageNumber.text = "תמונה ${position + 1}"

        Glide.with(this)
            .load(uri)
            .into(binding.ivFullScreenImage)
    }

    private fun setupGestures() {
        // זום בעזרת אצבעות
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 5.0f)
                binding.ivFullScreenImage.scaleX = scaleFactor
                binding.ivFullScreenImage.scaleY = scaleFactor
                return true
            }
        })

        // דאבל-טאפ לאיפוס זום
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                scaleFactor = if (scaleFactor > 1.0f) 1.0f else 2.0f
                binding.ivFullScreenImage.animate()
                    .scaleX(scaleFactor)
                    .scaleY(scaleFactor)
                    .setDuration(200)
                    .start()
                return true
            }
        })

        binding.ivFullScreenImage.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        // הצגה/הסתרה של פקדים בלחיצה על המסך
        binding.ivFullScreenImage.setOnClickListener {
            val isVisible = binding.topControls.visibility == View.VISIBLE
            binding.topControls.visibility = if (isVisible) View.GONE else View.VISIBLE
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
}
