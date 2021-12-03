package com.sample.nanit.extensions

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object Utils {

    // Copying uri contents to a file.
    suspend fun Uri.toFile(context: Context, parent: File): File? = withContext(Dispatchers.IO) {
        val inputStream = try {
            context.contentResolver.openInputStream(this@toFile)
        } catch (e: Exception) { // FileNotFound | Security
            null
        }

        inputStream?.use { stream ->
            parent.apply {
                outputStream().let { fileOut ->
                    stream.copyTo(fileOut)
                }
            }
        }
    }
}