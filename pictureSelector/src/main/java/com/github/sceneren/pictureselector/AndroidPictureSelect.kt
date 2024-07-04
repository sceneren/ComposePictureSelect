package com.github.sceneren.pictureselector

import android.content.Context
import com.github.sceneren.pictureselector.engine.CompressEngine
import com.github.sceneren.pictureselector.style.WhiteStyle
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch


/**
 * @author guoqingshan
 * @date 2024/1/9/009
 * @description
 */
class AndroidPictureSelect constructor(
    private val context: Context,
) : IPictureSelect {
    override fun takePhoto(params: PictureSelectParams): Flow<Result<Media?>> {
        return callbackFlow {
            PictureSelector.create(context)
                .openCamera(params.asChooseMode())
                .setSandboxFileEngine { context, srcPath, mineType, call ->
                    if (call != null) {
                        val sandboxPath =
                            SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
                        call.onCallback(srcPath, sandboxPath)
                    }
                }
                // 裁剪
                .apply {
                    if (params.isCrop) {
                        setCropEngine(params.cropEngine)
                    }
                }
                // 压缩
                .apply {
                    if (params.isCompress) {
                        setCompressEngine(CompressEngine(params.maxFileKbSize.toInt()))
                    }
                }
                .forResultActivity(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: ArrayList<LocalMedia?>) {
                        val mediaList = result.mapNotNull { localMedia ->
                            localMedia.asMedia()
                        }
                        trySendBlocking(Result.success(mediaList.firstOrNull()))
                    }

                    override fun onCancel() {
                        close(Throwable("取消选择"))
                    }

                })
            awaitClose {

            }
        }
            .catch { e ->
                e.printStackTrace()

                emit(Result.failure(e))
            }
    }

    override fun selectPhoto(params: PictureSelectParams): Flow<Result<List<Media>>> {
        val maxNum = params.maxImageNum + params.maxVideoNum
        val withSelectVideoImages = params.maxImageNum != 0 || params.maxVideoNum != 0
        return callbackFlow {
            PictureSelector.create(context)
                .openGallery(params.asChooseMode())
                .isDirectReturnSingle(true)
                .isFastSlidingSelect(true)
                // 文件沙盒
                .setSandboxFileEngine { context, srcPath, mineType, call ->
                    if (call != null) {
                        val sandboxPath =
                            SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
                        call.onCallback(srcPath, sandboxPath)
                    }
                }
                // 裁剪
                .apply {
                    if (params.isCrop) {
                        setCropEngine(params.cropEngine)
                    }
                }
                // 压缩
                .apply {
                    if (params.isCompress) {
                        setCompressEngine(CompressEngine(params.maxFileKbSize.toInt()))
                    }
                }
                .setMaxSelectNum(maxNum)
                .setMaxVideoSelectNum(params.maxVideoNum)
                .isDisplayCamera(params.allowTakePicture)
                .setImageEngine(params.imageEngine)
                .setSelectionMode(if (maxNum > 1) SelectModeConfig.MULTIPLE else SelectModeConfig.SINGLE)
                .isWithSelectVideoImage(withSelectVideoImages)
                .setSelectorUIStyle(WhiteStyle.getStyle(context, params.isWhiteStyle))
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: ArrayList<LocalMedia?>) {
                        val mediaList = result.mapNotNull { localMedia ->
                            localMedia.asMedia()
                        }
                        trySendBlocking(Result.success(mediaList))
                    }

                    override fun onCancel() {
                        close(Throwable("取消选择"))
                    }
                })

            awaitClose {

            }
        }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    /**
     * 通过传入的请求参数判断当前模式
     */
    private fun PictureSelectParams.asChooseMode(): Int {
        require(this.maxImageNum != 0 || this.maxVideoNum != 0)
        return if (this.maxImageNum > 0 && this.maxVideoNum > 0) {
            SelectMimeType.ofAll()
        } else if (this.maxVideoNum > 0) {
            SelectMimeType.ofVideo()
        } else {
            SelectMimeType.ofImage()
        }
    }

    internal fun LocalMedia?.asMedia(): Media? {
        if (this == null) {
            return null
        }
        val fileName = this.fileName
        val path = this.availablePath
        return Media(
            name = fileName,
            path = path,
            preview = AndroidBitmap(path)
        )
    }
}