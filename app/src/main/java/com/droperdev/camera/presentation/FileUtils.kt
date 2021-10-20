package com.droperdev.camera.presentation

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import com.droperdev.camera.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileUtils {
    companion object {
        @JvmStatic
        fun createImageFile(context: Context): File {
            val timeStamp =
                SimpleDateFormat("ddMMyyyy_HHmmss.SSS", Locale.getDefault()).format(Date())
            val imageName = "JPEG_${timeStamp}}"
            val storageDir = getAlbumDir(context)
            return File.createTempFile(imageName, ".jpg", storageDir)
        }

        @JvmStatic
        private fun getAlbumDir(context: Context): File? {
            var storageDir: File? = null
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                storageDir = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    getAlbumName()
                )

                if (!storageDir.mkdir()) {
                    if (!storageDir.exists()) {
                        return null
                    }
                }
            }
            return storageDir
        }

        @JvmStatic
        fun getUri(file: File): Uri {
            return Uri.fromFile(file)
        }

        private fun getAlbumName(): String = BuildConfig.APPLICATION_ID
    }
}