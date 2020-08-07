package com.mercuriy94.gameoflife.widget.life.base

import android.graphics.RectF

/**
 * @author Nikita Marsyukov
 */
data class MeasureParams(
    val desiredHeight: Float,
    val desiredWidth: Float,
    val width: Int,
    val height: Int,
    val maxScaleFactor: Float,
    val midScaleFactor: Float,
    val minScaleFactor: Float
)
