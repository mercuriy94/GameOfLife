package com.mercuriy94.gameoflife.widget.life.base

import android.view.View
import kotlin.math.min

/**
 * @author Nikita Marsyukov
 */
//sealed class MatrixScaleType2 {
//
//    abstract fun measure(params: MeasureParams): MeasureData
//
//}
//
//
//object FitStartScaleType2 : MatrixScaleType2() {
//
//    override fun measure(params: MeasureParams): MeasureData = buildMeasureData {
//
//        //width
//        val width = computeMeasuredSide(params.widthMeasureSpec, params.contentWidth)
//        widthMode = width.first
//        measuredWidth = width.second
//
//        //height
//        val height = computeMeasuredSide(params.heightMeasureSpec, params.contentHeight)
//        heightMode = height.first
//        measuredHeight = height.second
//
//
//
//        val scaleFactor = min(
//            measuredWidth / params.contentWidth,
//            measuredHeight / params.contentHeight
//        )
//
//        scaleFactorX = scaleFactor
//        scaleFactorY = scaleFactor
//
//        estimatedMinScaleFactorX = scaleFactorX * params.minScaleFactor
//        estimatedMinScaleFactorY = scaleFactorY * params.minScaleFactor
//
//        estimatedMaxScaleFactorX = roundFloat(
//            value = scaleFactorX * params.maxScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//        estimatedMaxScaleFactorY = roundFloat(
//            value = scaleFactorY * params.maxScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//
//        estimatedMidScaleFactorX = roundFloat(
//            value = scaleFactorX * params.midScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//        estimatedMidScaleFactorY = roundFloat(
//            value = scaleFactorY * params.midScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//        estimatedMinScaleFactorX = roundFloat(
//            value = scaleFactorX * params.minScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//        estimatedMinScaleFactorY = roundFloat(
//            value = scaleFactorY * params.minScaleFactor,
//            scale = ROUND_DEFAULT_SCALE_FLOAT
//        )
//
//    }
//
//
//    private fun computeMeasuredSide(
//        measureSpec: Int,
//        viewSideSize: Float
//    ): Pair<Int, Float> {
//        val measureMode = View.MeasureSpec.getMode(measureSpec)
//        val viewWidth = View.MeasureSpec.getSize(measureSpec)
//
//        return measureMode to when (measureMode) {
//            View.MeasureSpec.EXACTLY -> viewWidth.toFloat()
//            View.MeasureSpec.UNSPECIFIED -> viewSideSize
//            else -> min(viewSideSize, viewWidth.toFloat())
//        }
//    }
//
//}
//
//
