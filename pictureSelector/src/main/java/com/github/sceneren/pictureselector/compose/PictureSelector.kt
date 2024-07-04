package com.github.sceneren.pictureselector.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.github.sceneren.pictureselector.AndroidPictureSelect
import com.github.sceneren.pictureselector.IPictureSelect

@Composable
fun rememberPictureSelect(): IPictureSelect {
    val context = LocalContext.current
    return remember(context) {
        AndroidPictureSelect(context = context)
    }
}