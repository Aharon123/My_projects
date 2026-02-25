package com.example.imagegallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imagegallery.databinding.ItemImageBinding

class ImageGalleryAdapter(
    private val onImageClick: (Uri, Int) -> Unit,
    private val onImageRemove: (Int) -> Unit
) : RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>() {

    private val images = mutableListOf<Uri>()

    fun addImages(uris: List<Uri>) {
        val startPos = images.size
        images.addAll(uris)
        notifyItemRangeInserted(startPos, uris.size)
    }

    fun removeImage(position: Int) {
        if (position in images.indices) {
            images.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, images.size)
        }
    }

    fun clearAll() {
        val size = images.size
        images.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], position)
    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri, position: Int) {
            Glide.with(binding.ivImage.context)
                .load(uri)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.1f)
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                onImageClick(uri, position)
            }

            binding.btnRemove.setOnClickListener {
                val currentPos = adapterPosition
                if (currentPos != RecyclerView.NO_ID.toInt()) {
                    onImageRemove(currentPos)
                }
            }
        }
    }
}
