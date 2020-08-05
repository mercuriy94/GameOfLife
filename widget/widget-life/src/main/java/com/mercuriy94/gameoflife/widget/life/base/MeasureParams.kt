package com.mercuriy94.gameoflife.widget.life.base

import android.view.View
import kotlin.math.min

/**
 * @author Nikita Marsyukov
 */
data class MeasureParams(
    val widthMeasureSpec: Int,
    val heightMeasureSpec: Int,
    val contentWidth: Float,
    val contentHeight: Float,
    val maxScaleFactor: Float,
    val midScaleFactor: Float,
    val minScaleFactor: Float
)
