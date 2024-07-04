package com.github.sceneren.pictureselector.style

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.sceneren.pictureselector.R
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.PictureWindowAnimationStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle


object WhiteStyle {

    private var whiteSelectorStyle: PictureSelectorStyle? = null

    private val scaleEnterAnim by lazy {
        PictureWindowAnimationStyle().apply {
            setActivityEnterAnimation(com.luck.picture.lib.R.anim.ps_anim_enter)
            setActivityExitAnimation(com.luck.picture.lib.R.anim.ps_anim_exit)
        }

    }

    private val bottomEnterAnim by lazy {
        PictureWindowAnimationStyle().apply {
            setActivityEnterAnimation(com.luck.picture.lib.R.anim.ps_anim_up_in)
            setActivityExitAnimation(com.luck.picture.lib.R.anim.ps_anim_down_out)
        }
    }

    fun getStyle(context: Context, isWhite: Boolean, bottomOpen: Boolean): PictureSelectorStyle {
        return if (isWhite) {
            getWhiteStyle(context = context)
        } else {
            getBlackStyle()
        }.apply {
            windowAnimationStyle = if (bottomOpen) {
                bottomEnterAnim
            } else {
                scaleEnterAnim
            }
        }
    }

    private fun getWhiteStyle(context: Context): PictureSelectorStyle {
        if (whiteSelectorStyle == null) {
            whiteSelectorStyle = createWhiteStyle(context)
        }
        return whiteSelectorStyle!!
    }

    private fun getBlackStyle(): PictureSelectorStyle {
        return PictureSelectorStyle()
    }


    private fun createWhiteStyle(context: Context): PictureSelectorStyle {
        val whiteTitleBarStyle = TitleBarStyle()
        whiteTitleBarStyle.titleBackgroundColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_white
        )
        whiteTitleBarStyle.titleDrawableRightResource = R.drawable.ic_orange_arrow_down
        whiteTitleBarStyle.titleLeftBackResource = com.luck.picture.lib.R.drawable.ps_ic_black_back
        whiteTitleBarStyle.titleTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_black
        )
        whiteTitleBarStyle.titleCancelTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_53575e
        )
        whiteTitleBarStyle.isDisplayTitleBarLine = true

        val whiteBottomNavBarStyle = BottomNavBarStyle()
        whiteBottomNavBarStyle.bottomNarBarBackgroundColor = Color.parseColor("#EEEEEE")
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_53575e
        )

        whiteBottomNavBarStyle.bottomPreviewNormalTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_9b
        )
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_fa632d
        )
        whiteBottomNavBarStyle.isCompleteCountTips = false
        whiteBottomNavBarStyle.bottomEditorTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_53575e
        )
        whiteBottomNavBarStyle.bottomOriginalTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_53575e
        )

        val selectMainStyle = SelectMainStyle()
        selectMainStyle.statusBarColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_white
        )
        selectMainStyle.isDarkStatusBarBlack = true
        selectMainStyle.selectNormalTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_9b
        )
        selectMainStyle.selectTextColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_fa632d
        )
        selectMainStyle.previewSelectBackground = R.drawable.ps_demo_white_preview_selector
        selectMainStyle.selectBackground = com.luck.picture.lib.R.drawable.ps_checkbox_selector
        selectMainStyle.setSelectText(com.luck.picture.lib.R.string.ps_done_front_num)
        selectMainStyle.mainListBackgroundColor = ContextCompat.getColor(
            context,
            com.luck.picture.lib.R.color.ps_color_white
        )

        val selectorStyle = PictureSelectorStyle().apply {
            titleBarStyle = whiteTitleBarStyle
            bottomBarStyle = whiteBottomNavBarStyle
            setSelectMainStyle(selectMainStyle)
        }
        return selectorStyle

    }


}