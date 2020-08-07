package com.mercuriy94.gameoflife.widget.life.base

/**
 * @author Nikita Marsyukov
 */
enum class MatrixScaleType {

    FIT_START {

        override fun measure(measureParams: MeasureParams): MeasureData = buildMeasureData {
            width = measureParams.width
            height = measureParams.height

            val scale = ((measureParams.width) / measureParams.desiredWidth)
                .coerceAtMost(measureParams.height / measureParams.desiredHeight)

            scaleFactorX = scale
            scaleFactorY = scale
            estimatedMaxScales(measureParams)
            estimatedMidScales(measureParams)
            estimatedMinScales(measureParams)
        }
    };

    protected fun MeasureData.Builder.estimatedMaxScales(
        measureParams: MeasureParams
    ): MeasureData.Builder =
        estimatedMaxScaleFactorX(roundFloat(scaleFactorX * measureParams.maxScaleFactor))
            .estimatedMaxScaleFactorX(roundFloat(scaleFactorY * measureParams.maxScaleFactor))

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
