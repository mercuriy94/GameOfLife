package com.mercuriy94.gameoflife.widget.life.base

import android.view.View
import kotlin.math.min

/**
 * @author Nikita Marsyukov
 */
data class MeasureData2(
    val widthMode: Int,
    val measuredWidth: Float,
    val heightMode: Int,
    val measuredHeight: Float,
    val scaleFactorX: Float,
    val scaleFactorY: Float,
    val transX: Float,
    val transY: Float,
    val estimatedMaxScaleFactorX: Float,
    val estimatedMaxScaleFactorY: Float,
    val estimatedMinScaleFactorX: Float,
    val estimatedMinScaleFactorY: Float,
    val estimatedMidScaleFactorX: Float,
    val estimatedMidScaleFactorY: Float
)

class MeasureDataBuilder {
    var widthMode: Int = 0
    var measuredWidth: Float = 0f
    var heightMode: Int = 0
    var measuredHeight: Float = 0f
    var scaleFactorX: Float = 0f
    var scaleFactorY: Float = 0f
    var transX: Float = 0f
    var transY: Float = 0f
    var estimatedMaxScaleFactorX: Float = 0f
    var estimatedMaxScaleFactorY: Float = 0f
    var estimatedMinScaleFactorX: Float = 0f
    var estimatedMinScaleFactorY: Float = 0f
    var estimatedMidScaleFactorX: Float = 0f
    var estimatedMidScaleFactorY: Float = 0f

    fun build(): MeasureData2 = MeasureData2(
        widthMode = widthMode,
        measuredWidth = measuredWidth,
        heightMode = heightMode,
        measuredHeight = measuredHeight,
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        transX = transX,
        transY = transY,
        estimatedMaxScaleFactorX = estimatedMaxScaleFactorX,
        estimatedMaxScaleFactorY = estimatedMaxScaleFactorY,
        estimatedMinScaleFactorX = estimatedMinScaleFactorX,
        estimatedMinScaleFactorY = estimatedMinScaleFactorY,
        estimatedMidScaleFactorX = estimatedMidScaleFactorX,
        estimatedMidScaleFactorY = estimatedMidScaleFactorY
    )

}

inline fun buildMeasureData(setup: MeasureDataBuilder.() -> Unit): MeasureData2 {
    val builder = MeasureDataBuilder()
    builder.setup()
    return builder.build()
}