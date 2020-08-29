package com.mercuriy94.gameoflife.widget.life.base

import android.graphics.RectF

/**
 * @author Nikita Marsyukov
 */
data class MeasureParams(
    val widthMeasureSpec: Int,
    val heightMeasureSpec: Int,
    val desiredHeight: Int,
    val desiredWidth: Int,
    val resolvedWidth: Int,
    val resolvedHeight: Int,
    val maxScaleFactor: Float,
    val midScaleFactor: Float,
    val minScaleFactor: Float,
    val paddingLeft: Int,
    val paddingTop: Int,
    val paddingRight: Int,
    val paddingBottom: Int
)
