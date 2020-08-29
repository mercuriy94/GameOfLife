package com.mercuriy94.gameoflife.widget.life

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.mercuriy94.gameoflife.widget.life.base.*
import kotlin.math.max


/**
 * @author Nikita Marsyukov
 */
class LifeFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), OnTouchTransformListener {

    private val LIFE_SIZE = 700
    private val linePaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    companion object {
        private const val POINT_RADIUS = 16f
        private const val DEFAULT_MAX_SCALE = 5f
        private const val DEFAULT_MID_SCALE = 2.5f
        private const val DEFAULT_MIN_SCALE = 1f
    }

    private var array: FloatArray? = null
    private val lifeGeneration = Array(LIFE_SIZE) { BooleanArray(LIFE_SIZE) }
    private var maxScaleFactor = 0f
    private var minScaleFactor = 0f
    private var midScaleFactor = 0f

    private lateinit var measureData: MeasureData
    private lateinit var matrixScaleType: MatrixScaleType
    private val fieldMatrix = Matrix()

    private var touchHelper = ImageTouchHelper(this)

    init {
        initDefaultAttrs()
    }

    private fun initDefaultAttrs() {
        maxScaleFactor = DEFAULT_MAX_SCALE
        midScaleFactor = DEFAULT_MID_SCALE
        minScaleFactor = DEFAULT_MIN_SCALE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val pintRadius = POINT_RADIUS.toInt()
        val desiredWidth = (pintRadius * LIFE_SIZE) + paddingStart + paddingEnd
        val desiredHeight = (pintRadius * LIFE_SIZE) + paddingTop + paddingBottom

        val resolvedWidth =
            max(resolveSizeAndState(desiredWidth, widthMeasureSpec, 0), suggestedMinimumWidth)
        val resolvedHeight =
            max(resolveSizeAndState(desiredHeight, heightMeasureSpec, 0), suggestedMinimumHeight)

        val measureParams = collectMeasureParams(
            widthMeasureSpec = widthMeasureSpec,
            heightMeasureSpec = heightMeasureSpec,
            resolvedWidth = resolvedWidth,
            desiredWidth = desiredWidth,
            resolvedHeight = resolvedHeight,
            desiredHeight = desiredHeight
        )

        matrixScaleType = MatrixScaleType.FIT_CENTER

        measureData = matrixScaleType.measure(measureParams)

        val matrix = Matrix()

        matrix.postScale(measureData.estimatedMinScaleFactorX, measureData.estimatedMinScaleFactorY)
        matrix.postTranslate(measureData.transX, measureData.transY)
        setMatrix(matrix)

        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    private fun collectMeasureParams(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int,
        resolvedWidth: Int,
        desiredWidth: Int,
        resolvedHeight: Int,
        desiredHeight: Int
    ): MeasureParams =
        MeasureParams(
            widthMeasureSpec = widthMeasureSpec,
            heightMeasureSpec = heightMeasureSpec,
            resolvedWidth = resolvedWidth,
            desiredWidth = desiredWidth,
            resolvedHeight = resolvedHeight,
            desiredHeight = desiredHeight,
            minScaleFactor = minScaleFactor,
            midScaleFactor = midScaleFactor,
            maxScaleFactor = maxScaleFactor,
            paddingLeft = paddingLeft,
            paddingTop = paddingTop,
            paddingRight = paddingRight,
            paddingBottom = paddingBottom
        )

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //Подготовить массив линий для рисования сетки
        //Рассчитываем размер массива, где каждые 4 элемента массива соответсвуют координатам одной линии (x0, y0, x1, y1)
        val size = (LIFE_SIZE + 1) * 8
        array = FloatArray(size).also { array ->

            //индекс смещение каждой линии
            var sideOffset = 0

            for (i in 0 until size step 8) {

                array[i] = sideOffset * POINT_RADIUS
                array[i + 1] = 0f
                array[i + 2] = sideOffset * POINT_RADIUS
                array[i + 3] = LIFE_SIZE * POINT_RADIUS

                array[i + 4] = 0f
                array[i + 5] = sideOffset * POINT_RADIUS
                array[i + 6] = LIFE_SIZE * POINT_RADIUS
                array[i + 7] = sideOffset * POINT_RADIUS

                sideOffset += 1
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {

            save()
            setMatrix(fieldMatrix)

            array?.also { drawLines(it, linePaint) }
            lifeGeneration.forEachIndexed { x, column ->
                column.forEachIndexed { y, isAlive ->
                    if (isAlive) {
                        drawOval(
                            x * POINT_RADIUS,
                            y * POINT_RADIUS,
                            x * POINT_RADIUS + POINT_RADIUS,
                            y * POINT_RADIUS + POINT_RADIUS,
                            linePaint
                        )
                    }
                }
            }

            restore()

        }
    }

    fun fill(lifeField: Array<BooleanArray>) {
        lifeField.forEachIndexed { index, array -> array.copyInto(lifeGeneration[index]) }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean =
        touchHelper.onTouch(event, Matrix(fieldMatrix), measureData)

    private fun setMatrix(matrix: Matrix) {
        if (fieldMatrix != matrix) {
            fieldMatrix.set(matrix)
            invalidate()
        }
    }

    override fun onApplyMatrix(matrix: Matrix) {
        setMatrix(matrix)
    }
}

