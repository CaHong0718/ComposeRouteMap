package com.example.composeroutemap.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    private var current: Toast? = null

    fun show(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
        current?.cancel()
        current = Toast.makeText(context, message, length).also { it.show() }
    }
}