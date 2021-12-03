package com.sample.nanit.extensions

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

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

    fun getPrettyDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

    fun monthsFrom(date: Long): Int {
        val userEnteredDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date))
        val currentDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        val years = Period.between(userEnteredDate, currentDate).years
        val months = Period.between(userEnteredDate, currentDate).months

        return years * 12 + months
    }

    fun yearsFrom(date: Long): Int {
        val userEnteredDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date))
        val currentDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        val years = Period.between(userEnteredDate, currentDate).years

        return years
    }
}