package com.puc.correios.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtils {
    private const val COMPLETE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    @SuppressLint("SimpleDateFormat")
    fun converterData(dataOriginal: String): String {
        val originalPattern = SimpleDateFormat(COMPLETE_DATE_PATTERN)
        val newPattern = SimpleDateFormat(COMPLETE_DATE_PATTERN)

        return try {
            val data = checkNotNull(originalPattern.parse(dataOriginal))
            newPattern.format(data)
        } catch (e: Exception) {
            e.printStackTrace()
            "Data inválida"
        }
    }

}