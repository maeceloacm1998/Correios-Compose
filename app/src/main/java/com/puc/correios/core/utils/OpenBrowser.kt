package com.puc.correios.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

object OpenBrowser {
    fun url(context: Context, url: String) {
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(context, intent, null)
        }
    }
}