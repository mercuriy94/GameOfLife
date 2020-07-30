package com.mercuriy94.gameoflife.presentation.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

/**
 * @author Nikita Marsyukov
 */
class GameOfLifeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val linePaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    companion object {

        private const val POINT_RADIUS = 16f
        private const val LIFE_SIZE = 60
    }

    private var array: FloatArray? = null
    private val lifeGeneration = Array(LIFE_SIZE) { BooleanArray(
        LIFE_SIZE
    ) }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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

            array?.also { drawLines(it, linePaint) }
            lifeGeneration.forEachIndexed { x, column ->
                column.forEachIndexed { y, isAlive ->
                    if (isAlive) {
                        drawOval(x * POINT_RADIUS, y * POINT_RADIUS, x * POINT_RADIUS + POINT_RADIUS, y * POINT_RADIUS + POINT_RADIUS, linePaint)
                    }
                }
            }

        }
    }

    fun fill() {
        for (x in 0 until LIFE_SIZE) {
            for (y in 0 until LIFE_SIZE) {
                lifeGeneration[x][y] = Random.nextBoolean()
            }
        }

        invalidate()
    }

    fun start() {
    }

    fun stop() {
    }
}