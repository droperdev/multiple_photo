package com.droperdev.camera.presentation.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.droperdev.camera.BuildConfig
import com.droperdev.camera.databinding.FragmentMultiplePhotoBinding
import com.droperdev.camera.presentation.FileUtils.Companion.createImageFile
import com.droperdev.camera.presentation.FileUtils.Companion.getUri
import com.droperdev.camera.presentation.ui.Photo.Companion.getPhotos

class MultiplePhotoFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentMultiplePhotoBinding
    private var path: String = ""

    private var multiplePhotoAdapter: MultiplePhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMultiplePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        eventUI()
    }

    private fun initUI() {
        setupAdapter()
    }

    private fun eventUI() {

    }

    private fun setupAdapter() {
        multiplePhotoAdapter = MultiplePhotoAdapter(getPhotos(), this)
        binding.rvMultiplePhoto.apply {
            adapter = multiplePhotoAdapter
        }
    }

    override fun onEmptyItemClick(photo: Photo) {
        takePhoto()
    }

    override fun onDeleteItemClick(photo: Photo) {
        TODO("Not yet implemented")
    }

    override fun onEditItemClick(photo: Photo) {
        TODO("Not yet implemented")
    }

    private fun takePhoto() {
        val file = createImageFile(requireContext())
        path = file.absolutePath
        val photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            file
        )

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        if (takePhotoIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    if (path.isNotEmpty()) {
                        multiplePhotoAdapter?.addItemInPosition(
                            Photo(
                                localUrl = path,
                                isEmpty = false
                            )
                        )
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        val REQUEST_IMAGE_CAPTURE = 1
    }
}