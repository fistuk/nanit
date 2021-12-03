package com.sample.nanit.view

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.nanit.extensions.Utils.toFile
import com.sample.nanit.model.UserPreferences
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PhotoPickerViewModel @Inject constructor(val context: Context, private val userPreferences: UserPreferences) : ViewModel() {


    private val _babyPhoto = MutableLiveData<File>()
    val babyPhoto: LiveData<File>
        get() = _babyPhoto

    var selectedPhotoFile: File? = null

    init {
        viewModelScope.launch {
            val storedPhotoPath = userPreferences.getPhoto()
            if (storedPhotoPath != null) {
                _babyPhoto.value = File(storedPhotoPath)
            }
        }
    }

    fun getUriForCamera(): Uri {
        val photoFile = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }

        // TODO handle errors
        if (photoFile != null) {
            return FileProvider.getUriForFile(context, "com.sample.nanit.fileprovider", photoFile)
        } else throw IllegalStateException("Cant create photo file")
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for later use
            selectedPhotoFile = this
        }
    }

    fun cameraPhotoSelected() = viewModelScope.launch {
        _babyPhoto.value = selectedPhotoFile
        userPreferences.setPhotoPath(selectedPhotoFile!!.absolutePath)
    }

    fun galleryPhotoSelected(photoUri: Uri?) = viewModelScope.launch {
        if (photoUri != null) {
            photoUri.toFile(context, createImageFile())
            _babyPhoto.value = selectedPhotoFile
            userPreferences.setPhotoPath(selectedPhotoFile!!.absolutePath)
        } else {
            // TODO handle error
        }
    }
}