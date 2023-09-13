package com.example.head2head.view.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormatDate(date: String): String{
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        val expected = DateTimeFormatter.ofPattern("dd-MM-yyyy")


        return LocalDateTime.parse(date, format).format(expected)

    }
}