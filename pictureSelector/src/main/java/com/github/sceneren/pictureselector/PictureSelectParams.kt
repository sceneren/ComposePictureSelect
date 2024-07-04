package com.github.sceneren.pictureselector

import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.engine.ImageEngine

/**
 * @author guoqingshan
 * @date 2024/1/10/010
 * @description

 * @property maxImageNum Int            选择图片的最大数量
 * @property maxVideoNum Int            选择视频的最大数量
 * @property maxFileKbSize Long         选择图片最大大小 默认300KB
 * @property allowTakePicture Boolean   相册中是否允许拍照
 * @property isCrop Boolean             是否裁剪
 * @property isCompress Boolean         是否压缩
 * @constructor
 */
data class PictureSelectParams(
    val maxImageNum: Int,
    val maxVideoNum: Int = 0,
    val maxFileKbSize: Long = 300L,
    val isWhiteStyle: Boolean = true,
    val allowTakePicture: Boolean = true,
    val isCrop: Boolean = false,
    val isCompress: Boolean = true,
    val imageEngine: ImageEngine,
    val cropEngine: CropFileEngine,
)