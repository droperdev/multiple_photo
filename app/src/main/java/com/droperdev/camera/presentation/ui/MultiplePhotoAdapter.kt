package com.droperdev.camera.presentation.ui

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droperdev.camera.databinding.PhotoItemBinding
import java.io.File

class MultiplePhotoAdapter(
    private var photos: MutableList<Photo>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MultiplePhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiplePhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MultiplePhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MultiplePhotoViewHolder, position: Int) {
        holder.bind(photos[position], listener)
    }

    override fun getItemCount(): Int {
        return photos.size

    }

    fun addItemInPosition(photo: Photo) {
        if (photos.size == 4) {
            photos[photos.size - 1] = photo
            notifyItemChanged(photos.size - 1)
        } else {
            photos.add(photos.size - 1, photo)
            notifyItemMoved(photos.size - 2, photos.size - 1)
        }
    }
}

interface OnItemClickListener {
    fun onEmptyItemClick(photo: Photo)
    fun onDeleteItemClick(photo: Photo)
    fun onEditItemClick(photo: Photo)
}

class MultiplePhotoViewHolder(private val binding: PhotoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo, listener: OnItemClickListener) {
        binding.tvPhotoName.text = photo.name
        binding.btnDelete.visibility = if (photo.isEmpty) GONE else VISIBLE
        binding.ivPhotoEmpty.visibility = if (photo.isEmpty) VISIBLE else GONE
        binding.ivPhoto.visibility = if (photo.isEmpty) GONE else VISIBLE
        binding.llName.visibility = if (photo.isEmpty) GONE else VISIBLE

        Glide.with(itemView.context).load(File(photo.localUrl)).into(binding.ivPhoto)

        binding.ivPhotoEmpty.setOnClickListener {
            listener.onEmptyItemClick(photo)
        }

        binding.btnDelete.setOnClickListener {
            listener.onDeleteItemClick(photo)
        }
        binding.btnEdit.setOnClickListener {
            listener.onEditItemClick(photo)
        }
    }
}


class Photo(
    var name: String = "",
    var localUrl: String = "",
    var remoteUrl: String = "",
    var isEmpty: Boolean = true
) {
    companion object {
        @JvmStatic
        fun getPhotos() = mutableListOf(
            Photo()
        )
    }
}