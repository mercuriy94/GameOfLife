package com.mercuriy94.gameoflife.widget.life.base

/**
 * @author Nikita Marsyukov
 */
data class MeasureData(
    val width: Int,
    val height: Int,
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
) {

    internal constructor(builder: Builder) :
            this(
                width = builder.width,
                height = builder.height,
                scaleFactorX = builder.scaleFactorX,
                scaleFactorY = builder.scaleFactorY,
                transX = builder.transX,
                transY = builder.transY,
                estimatedMaxScaleFactorX = builder.estimatedMaxScaleFactorX,
                estimatedMaxScaleFactorY = builder.estimatedMaxScaleFactorY,
                estimatedMinScaleFactorX = builder.estimatedMinScaleFactorX,
                estimatedMinScaleFactorY = builder.estimatedMinScaleFactorY,
                estimatedMidScaleFactorX = builder.estimatedMidScaleFactorX,
                estimatedMidScaleFactorY = builder.estimatedMidScaleFactorY
            )

    class Builder {
        var width: Int = 0
        var height: Int = 0
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

        fun width(width: Int) = apply { this.width = width }
        fun height(height: Int) = apply { this.height = height }
        fun scaleFactorX(scaleFactorX: Float) = apply { this.scaleFactorX = scaleFactorX }
        fun scaleFactorY(scaleFactorY: Float) = apply { this.scaleFactorY = scaleFactorY }
        fun transX(transX: Float) = apply { this.transX = transX }
        fun transY(transY: Float) = apply { this.transY = transY }
        fun estimatedMaxScaleFactorX(estimatedMaxScaleFactorX: Float) =
            apply { this.estimatedMaxScaleFactorX = estimatedMaxScaleFactorX }

        fun estimatedMaxScaleFactorY(estimatedMaxScaleFactorY: Float) =
            apply { this.estimatedMaxScaleFactorY = estimatedMaxScaleFactorY }

        fun estimatedMinScaleFactorX(estimatedMinScaleFactorX: Float) =
            apply { this.estimatedMinScaleFactorX = estimatedMinScaleFactorX }

        fun estimatedMinScaleFactorY(estimatedMinScaleFactorY: Float) =
            apply { this.estimatedMinScaleFactorY = estimatedMinScaleFactorY }

        fun estimatedMidScaleFactorX(estimatedMidScaleFactorX: Float) =
            apply { this.estimatedMidScaleFactorX = estimatedMidScaleFactorX }

        fun estimatedMidScaleFactorY(estimatedMidScaleFactorY: Float) =
            apply { this.estimatedMidScaleFactorY = estimatedMidScaleFactorY }

        fun build(): MeasureData = MeasureData(this)

    }

}

inline fun buildMeasureData(setup: MeasureData.Builder.() -> Unit): MeasureData {
    val builder = MeasureData.Builder()
    builder.setup()
    return builder.build()
}