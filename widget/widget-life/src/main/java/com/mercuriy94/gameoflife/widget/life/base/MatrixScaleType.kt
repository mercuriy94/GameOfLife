package com.mercuriy94.gameoflife.widget.life.base

import android.view.View
import kotlin.math.min

/**
 * Параметры масштабирвания границ представления
 *
 * @author Nikita Marsyukov
 */
enum class MatrixScaleType {

    /**
     * Выравнивание представление по левой и верхней границ
     * */
    FIT_START {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.resolvedWidth
            height = measureParams.resolvedHeight

            val resolvedWidth: Float = (measureParams.resolvedWidth -
                    measureParams.paddingLeft -
                    measureParams.paddingRight).toFloat()

            val resolvedHeight: Float = (measureParams.resolvedHeight -
                    measureParams.paddingTop -
                    measureParams.paddingBottom).toFloat()

            val desiredWidth =
                measureParams.desiredWidth - measureParams.paddingLeft - measureParams.paddingRight

            val desiredHeight =
                measureParams.desiredHeight - measureParams.paddingTop - measureParams.paddingBottom

            val scale = (resolvedWidth / desiredWidth)
                .coerceAtMost(resolvedHeight / desiredHeight)

            scaleFactorX = scale
            scaleFactorY = scale
            estimatedMinScales(measureParams)
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)
            transX = measureParams.paddingLeft.toFloat()
            transY = measureParams.paddingTop.toFloat()
        }
    },

    /**
     * Выравнивание представление по нижней и правой границ
     * */
    FIT_END {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.resolvedWidth
            height = measureParams.resolvedHeight

            val resolvedWidth: Float = (measureParams.resolvedWidth -
                    measureParams.paddingLeft -
                    measureParams.paddingRight).toFloat()

            val resolvedHeight: Float = (measureParams.resolvedHeight -
                    measureParams.paddingTop -
                    measureParams.paddingBottom).toFloat()

            val desiredWidth =
                measureParams.desiredWidth - measureParams.paddingLeft - measureParams.paddingRight

            val desiredHeight =
                measureParams.desiredHeight - measureParams.paddingTop - measureParams.paddingBottom

            val scale = (resolvedWidth / desiredWidth)
                .coerceAtMost(resolvedHeight / desiredHeight)

            scaleFactorX = scale
            scaleFactorY = scale
            estimatedMinScales(measureParams)
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)

            transX =
                resolvedWidth - (desiredWidth * estimatedMinScaleFactorX) + measureParams.paddingLeft.toFloat()
            transY =
                resolvedHeight - (desiredHeight * estimatedMinScaleFactorY) + measureParams.paddingTop.toFloat()

        }
    },

    FIT_CENTER {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.resolvedWidth
            height = measureParams.resolvedHeight

            val resolvedWidth: Float = (measureParams.resolvedWidth -
                    measureParams.paddingLeft -
                    measureParams.paddingRight).toFloat()

            val resolvedHeight: Float = (measureParams.resolvedHeight -
                    measureParams.paddingTop -
                    measureParams.paddingBottom).toFloat()

            val desiredWidth =
                measureParams.desiredWidth - measureParams.paddingLeft - measureParams.paddingRight

            val desiredHeight =
                measureParams.desiredHeight - measureParams.paddingTop - measureParams.paddingBottom

            val scale = (resolvedWidth / desiredWidth)
                .coerceAtMost(resolvedHeight / desiredHeight)


            scaleFactorX = scale
            scaleFactorY = scale
            estimatedMinScales(measureParams)
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)

            transX =
                ((resolvedWidth - (desiredWidth * estimatedMinScaleFactorX)) / 2) + measureParams.paddingLeft.toFloat()
            transY =
                ((resolvedHeight - (desiredHeight * estimatedMinScaleFactorY)) / 2) + measureParams.paddingTop.toFloat()

        }
    },

    FIX_XY {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.resolvedWidth
            height = measureParams.resolvedHeight

            val resolvedWidth: Float = (measureParams.resolvedWidth -
                    measureParams.paddingLeft -
                    measureParams.paddingRight).toFloat()

            val resolvedHeight: Float = (measureParams.resolvedHeight -
                    measureParams.paddingTop -
                    measureParams.paddingBottom).toFloat()

            val desiredWidth =
                measureParams.desiredWidth - measureParams.paddingLeft - measureParams.paddingRight

            val desiredHeight =
                measureParams.desiredHeight - measureParams.paddingTop - measureParams.paddingBottom

            val widthMode = View.MeasureSpec.getMode(measureParams.widthMeasureSpec)
            scaleFactorX =
                when {
                    widthMode == View.MeasureSpec.EXACTLY ||
                            desiredWidth > resolvedWidth -> resolvedWidth / desiredWidth
                    else -> 1f
                }

            val heightMode = View.MeasureSpec.getMode(measureParams.heightMeasureSpec)

            scaleFactorY = when {
                heightMode == View.MeasureSpec.EXACTLY ||
                        desiredHeight > resolvedHeight -> resolvedHeight / desiredHeight
                else -> 1f
            }

            estimatedMinScales(measureParams)
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)

            transX =
                ((resolvedWidth - (desiredWidth * estimatedMinScaleFactorX)) / 2) + measureParams.paddingLeft.toFloat()
            transY =
                ((resolvedHeight - (desiredHeight * estimatedMinScaleFactorY)) / 2) + measureParams.paddingTop.toFloat()

        }
    },

    CENTER_INSIDE {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.resolvedWidth
            height = measureParams.resolvedHeight

            val resolvedWidth: Float = (measureParams.resolvedWidth -
                    measureParams.paddingLeft -
                    measureParams.paddingRight).toFloat()

            val resolvedHeight: Float = (measureParams.resolvedHeight -
                    measureParams.paddingTop -
                    measureParams.paddingBottom).toFloat()

            val desiredWidth =
                measureParams.desiredWidth - measureParams.paddingLeft - measureParams.paddingRight

            val desiredHeight =
                measureParams.desiredHeight - measureParams.paddingTop - measureParams.paddingBottom

            val scale = min(1f, min(resolvedWidth / desiredWidth, resolvedHeight / resolvedWidth))

            scaleFactorX = scale
            scaleFactorY = scale

            estimatedMinScales(measureParams)
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)

            transX =
                ((resolvedWidth - (desiredWidth * estimatedMinScaleFactorX)) / 2) + measureParams.paddingLeft.toFloat()
            transY =
                ((resolvedHeight - (desiredHeight * estimatedMinScaleFactorY)) / 2) + measureParams.paddingTop.toFloat()

        }
    };

    protected fun MeasureData.Builder.estimatedMaxScales(
        measureParams: MeasureParams
    ): MeasureData.Builder =
        estimatedMaxScaleFactorX(roundFloat(scaleFactorX * measureParams.maxScaleFactor))
            .estimatedMaxScaleFactorY(roundFloat(scaleFactorY * measureParams.maxScaleFactor))

    protected fun MeasureData.Builder.estimatedMidScales(
        measureParams: MeasureParams
    ): MeasureData.Builder =
        estimatedMidScaleFactorX(roundFloat(scaleFactorX * measureParams.midScaleFactor))
            .estimatedMidScaleFactorY(roundFloat(scaleFactorY * measureParams.midScaleFactor))

    protected fun MeasureData.Builder.estimatedMinScales(
        measureParams: MeasureParams
    ): MeasureData.Builder =
        estimatedMinScaleFactorX(roundFloat(scaleFactorX * measureParams.minScaleFactor))
            .estimatedMinScaleFactorY(roundFloat(scaleFactorY * measureParams.minScaleFactor))

    abstract fun measure(measureParams: MeasureParams): MeasureData

}
