package com.github.sceneren.pictureselector

import java.io.File

data class Media(
    val name: String,
    val path: String,
    val preview: Bitmap,
)

fun Media.fileSize(): Long {
    val file = File(path)
    if (file.exists() && file.isFile) {
        return file.length()
    }
    return 0L
}