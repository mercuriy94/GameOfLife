package com.mercuriy94.gameoflife.widget.life

import android.animation.FloatEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.*
import com.mercuriy94.gameoflife.widget.life.base.*
import kotlin.math.*


/**
 * @author Nikita Marsyukov
 */
class LifeFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val LIFE_SIZE = 300
    private val linePaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    companion object {
        private const val INVALID_POINTER_ID = -1
        private const val TAG = "LifeFieldView"
        private const val POINT_RADIUS = 16f
        private const val DEFAULT_MAX_SCALE = 5f
        private const val DEFAULT_MID_SCALE = 2.5f
        private const val DEFAULT_MIN_SCALE = 1f

        private const val MODE_NONE = 0
        private const val MODE_DRAG = 1 shl 0
        private const val MODE_ZOOM = 1 shl 1
    }

    private val myStiffness = 750f
    private val myDamping = SpringForce.DAMPING_RATIO_NO_BOUNCY

    private var array: FloatArray? = null
    private val lifeGeneration = Array(LIFE_SIZE) { BooleanArray(LIFE_SIZE) }
    private var maxScaleFactor = 0f
    private var minScaleFactor = 0f
    private var midScaleFactor = 0f

    private var oldDist = 1f
    private lateinit var measureData: MeasureData
    private lateinit var matrixScaleType: MatrixScaleType

    private val fieldMatrix = Matrix()

    private val gestureDetector = GestureDetector(getContext(), GestureListener())

    private val startTouchPoint = PointF()
    private val lastTouchPoint = PointF()
    private val pinchZoomMidPoint = PointF()
    private var activePointerId = INVALID_POINTER_ID
    private var mode: Int = MODE_NONE
    private var viewWidth = 0f
    private var viewHeight = 0f
    private var normFieldHeight = 0f
    private var normFieldWidth = 0f

    private val savedHelpMatrix = Matrix()
    private val floatEvaluator = FloatEvaluator()

    private val matrixValues: FloatArray = FloatArray(9)

    //region animators

    private var currentSpringAnimationTransX: SpringAnimation? = null
    private var currentSpringAnimationTransY: SpringAnimation? = null

    private var currentSpringAnimationScale: SpringAnimation? = null

    private var currentSpringAnimationFlingX: FlingAnimation? = null
    private var currentSpringAnimationFlingY: FlingAnimation? = null

    //endregion animators

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
        normFieldHeight = desiredHeight.toFloat()
        normFieldWidth = desiredWidth.toFloat()

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewHeight = h.toFloat()
        viewWidth = w.toFloat()

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = false
        val detectorTouchConsumed = gestureDetector.onTouchEvent(event)

        if (event != null) {

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "ACTION_DOWN")
                    handleActionDown(event)
                    result = true
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    Log.d(TAG, "ACTION_POINTER_DOWN")
                    handleActionPointerDown(event)
                    result = true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    Log.d(TAG, "ACTION_UP")
                    activePointerId = INVALID_POINTER_ID
                    mode = MODE_NONE
                    if (!detectorTouchConsumed) {
                        correctionField()
                    }
                    result = true
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    Log.d(TAG, "ACTION_POINTER_UP")
                    handleActionPointerUp(event)
                    result = true
                }
                MotionEvent.ACTION_MOVE -> {
//                    Log.d(TAG, "ACTION_MOVE")

                    if (mode and MODE_ZOOM != 0 && isEnableZoom()) {
                        handleZoomImage(event)
                    } else if (mode and MODE_DRAG != 0 && isEnableTrans()) {
                        handleDragImage(event)
                    }


                    result = true
                }
            }

        }

        return result
    }

    private fun correctionField() {
        if (isNeedCorrectionScale()) {
            correctionScale()
        } else if (isEnableStickyBounds()) {
            autoAlignment()
        }
    }

    private fun isNeedCorrectionScale(): Boolean {
        fieldMatrix.getValues(matrixValues)

        val scaleX = matrixValues[Matrix.MSCALE_X]
        val scaleY = matrixValues[Matrix.MSCALE_Y]

        return (scaleX < measureData.estimatedMinScaleFactorX || scaleX > measureData.estimatedMaxScaleFactorX) ||
                (scaleY < measureData.estimatedMinScaleFactorY || scaleY > measureData.estimatedMaxScaleFactorY)
    }

    private fun correctionScale() {
        savedHelpMatrix.set(fieldMatrix)
        savedHelpMatrix.getValues(matrixValues)

        val endScaleX = calculateCorrectionEndScale(
            matrixValues[Matrix.MSCALE_X],
            measureData.estimatedMaxScaleFactorX,
            measureData.estimatedMinScaleFactorX
        )
        val endScaleY = calculateCorrectionEndScale(
            matrixValues[Matrix.MSCALE_Y],
            measureData.estimatedMaxScaleFactorY,
            measureData.estimatedMinScaleFactorY
        )

        val anchorPointX = calculateAnchorPointPostScale(
            matrixValues[Matrix.MTRANS_X],
            matrixValues[Matrix.MSCALE_X],
            endScaleX,
            viewWidth,
            normFieldWidth,
            pinchZoomMidPoint.x
        )

        val anchorPointY = calculateAnchorPointPostScale(
            matrixValues[Matrix.MTRANS_Y],
            matrixValues[Matrix.MSCALE_Y],
            endScaleY,
            viewHeight,
            normFieldHeight,
            pinchZoomMidPoint.y
        )

        currentSpringAnimationScale = springAnimationOf(setter = { value ->

            fieldMatrix.set(savedHelpMatrix)
            val animateFraction = value / 1000f
            val scaleX = floatEvaluator.evaluate(animateFraction, 1f, endScaleX)
            val scaleY = floatEvaluator.evaluate(animateFraction, 1f, endScaleY)
            fieldMatrix.postScale(scaleX, scaleY, anchorPointX, anchorPointY)
            applyMatrix(fieldMatrix)

        }, getter = { 0f }, finalPosition = 1000f)
            .withSpringForceProperties {
                dampingRatio = myDamping
                stiffness = myStiffness
            }.apply {

                addEndListener { anim, canceled, _, _ ->

                    if (anim == currentSpringAnimationScale) {
                        currentSpringAnimationScale = null

                        if (!canceled) {
                            autoAlignment()
                        }

                    }
                }
            }

        currentSpringAnimationScale?.start()

    }

    private fun autoAlignment() {
        autoAlignmentX()
        autoAlignmentY()
    }

    private fun autoAlignmentX() {
        fieldMatrix.getValues(matrixValues)

        currentSpringAnimationTransX = springAnimationOf(
            setter = { value ->
                matrixValues[Matrix.MTRANS_X] = value
                fieldMatrix.setValues(matrixValues)
                applyMatrix(fieldMatrix)
            }, getter = {
                fieldMatrix.setValues(matrixValues)
                matrixValues[Matrix.MTRANS_X]
            },
            finalPosition = matrixValues[Matrix.MTRANS_X] +
                    calculateSpaceFixTrans(
                        viewWidth,
                        matrixValues[Matrix.MSCALE_X] * normFieldWidth,
                        matrixValues[Matrix.MTRANS_X]
                    )
        ).withSpringForceProperties {
            dampingRatio = myDamping
            stiffness = myStiffness
        }.apply {
            addEndListener { anim, _, _, _ ->
                if (anim == currentSpringAnimationTransX) {
                    currentSpringAnimationTransX = null
                }
            }
        }.also {
            it.start()
        }
    }

    private fun autoAlignmentY() {
        fieldMatrix.getValues(matrixValues)

        currentSpringAnimationTransY = springAnimationOf(
            setter = { value ->
                matrixValues[Matrix.MTRANS_Y] = value
                fieldMatrix.setValues(matrixValues)
                applyMatrix(fieldMatrix)
            }, getter = {
                fieldMatrix.setValues(matrixValues)
                matrixValues[Matrix.MTRANS_Y]
            },
            finalPosition = matrixValues[Matrix.MTRANS_Y] +
                    calculateSpaceFixTrans(
                        viewHeight,
                        matrixValues[Matrix.MSCALE_Y] * normFieldHeight,
                        matrixValues[Matrix.MTRANS_Y]
                    )
        ).withSpringForceProperties {
            dampingRatio = myDamping
            stiffness = myStiffness
        }.apply {
            addEndListener { anim, _, _, _ ->
                if (anim == currentSpringAnimationTransY) {
                    currentSpringAnimationTransY = null
                }
            }
        }.also {
            it.start()
        }

    }

    private fun handleActionDown(motionEvent: MotionEvent) {
        invalidateAnimation()
        mode = mode or MODE_DRAG
        fieldMatrix.getValues(matrixValues)
        val curr = PointF(motionEvent.x, motionEvent.y)
        startTouchPoint.set(curr)
        lastTouchPoint.set(curr)
        activePointerId = motionEvent.getPointerId(0)
    }

    private fun handleActionPointerDown(motionEvent: MotionEvent) {
        invalidateAnimation()
        val oldDist = computeSpacing(motionEvent)
        midPoint(pinchZoomMidPoint, motionEvent)
        if (oldDist > 25f) {
            mode = mode or MODE_ZOOM
        }
        this.oldDist = oldDist
    }

    private fun handleActionPointerUp(motionEvent: MotionEvent) {
        if (motionEvent.pointerCount == 2) {
            mode = MODE_DRAG
        }
        val pointerIndex = motionEvent.actionIndex
        val pointerId = motionEvent.getPointerId(pointerIndex)

        if (pointerId == activePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            activePointerId = motionEvent.getPointerId(newPointerIndex)
        }

        val curr = PointF(
            motionEvent.getX(motionEvent.findPointerIndex(activePointerId)),
            motionEvent.getY(motionEvent.findPointerIndex(activePointerId))
        )

        startTouchPoint.set(curr)
        lastTouchPoint.set(curr)

    }

    private fun handleZoomImage(motionEvent: MotionEvent) {
        val newDist = computeSpacing(motionEvent)
        Log.d(TAG, "handleZoomImage.newDist = $newDist")
        if (newDist > 25f) {
            val scale: Float = newDist / oldDist
            fieldMatrix.postScale(scale, scale, pinchZoomMidPoint.x, pinchZoomMidPoint.y)
            this.oldDist = newDist
            applyMatrix(fieldMatrix)
        }
    }

    private fun computeSpacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2f, y / 2f)
    }

    private fun handleDragImage(motionEvent: MotionEvent) {
        Log.d(TAG, "handleDragImage")

        val pointerIndex = motionEvent.findPointerIndex(activePointerId)

        val curr = PointF(motionEvent.getX(pointerIndex), motionEvent.getY(pointerIndex))
        val transX = calculateDragOffset(
            originalOffset = curr.x - lastTouchPoint.x,
            trans = matrixValues[Matrix.MTRANS_X],
            viewSize = viewWidth,
            fieldSize = matrixValues[Matrix.MSCALE_X] * normFieldWidth
        )
        val transY = calculateDragOffset(
            originalOffset = curr.y - lastTouchPoint.y,
            trans = matrixValues[Matrix.MTRANS_Y],
            viewSize = viewHeight,
            fieldSize = matrixValues[Matrix.MSCALE_Y] * normFieldHeight
        )
        fieldMatrix.postTranslate(transX, transY)
        lastTouchPoint.set(curr)
        applyMatrix(fieldMatrix)
        logTransFromMatrix(fieldMatrix)

    }

    private fun calculateCorrectionEndScale(scale: Float, maxScale: Float, minScale: Float): Float =
        when {
            scale > maxScale -> maxScale / scale
            else -> minScale / scale
        }

    private fun calculateDragOffset(
        originalOffset: Float,
        trans: Float,
        viewSize: Float,
        fieldSize: Float
    ): Float {

        val result: Float

        if (fieldSize > viewSize) {
            //Если размер игрового поля больше размера вью

            val reserve = viewSize - (fieldSize + trans)

            if (trans < 0f) {

                //если изображение смещено от начала оси
                //дальше выисления строятся на основе направления перемещения пальца и флага enableTrans

                //если изображение смещено от начала оси
                //дальше выисления строятся на основе направления перемещения пальца и флага enableTrans
                result = if (originalOffset < 0) {
                    if (isEnableTrans() || originalOffset > reserve) {
                        when {
                            reserve < 0f -> when {
                                isEnableScrolling() -> originalOffset
                                else -> 0f
                            }
                            isEnableStickyBounds() -> originalOffset / 2f
                            else -> originalOffset
                        }
                    } else {
                        reserve
                    }
                } else {
                    if (isEnableTrans()) {
                        if (isEnableScrolling()) originalOffset else 0f
                    } else {
                        if (trans + originalOffset <= 0f) if (isEnableScrolling()) originalOffset else 0f else 0f - trans
                    }
                }

            } else {

                //если изображение не смещено от начала оси, или смещенно в противоположную сторону
                result = if (originalOffset < 0) {
                    //дальше выисления строятся на основе направления перемещения пальца и флага enableTrans
                    if (isEnableTrans() || originalOffset > reserve) {
                        if (isEnableScrolling()) originalOffset else 0f
                    } else {
                        reserve
                    }
                } else {
                    when {
                        isEnableTrans() -> when {
                            isEnableStickyBounds() -> originalOffset / 2f
                            else -> originalOffset
                        }
                        else -> 0f
                    }
                }
            }


        } else {
            //Если размер игрового поля меньше или равен размеру вью
            result =
                when {
                    isEnableTrans() -> when {
                        isEnableStickyBounds() -> originalOffset / 2f
                        else -> originalOffset
                    }
                    else -> 0f
                }
        }


        return result
    }

    private fun calculateSpaceFixTrans(
        viewSize: Float,
        contentSize: Float,
        trans: Float
    ): Float {

        var offsetTrans = 0f
        if (contentSize < viewSize) {
            offsetTrans = when (matrixScaleType) {
                MatrixScaleType.FIT_START -> {
                    -trans
                }
                MatrixScaleType.FIT_END -> {
                    viewSize - contentSize - trans
                }
                else -> {
                    //for default - fit center
                    viewSize / 2f - contentSize / 2f - trans
                }
            }
        } else {
            if (trans < 0f) {
                val reserve = contentSize - (abs(trans) + viewSize)
                if (reserve < 0f) offsetTrans = -reserve
            } else {
                offsetTrans = -trans
            }
        }

        return offsetTrans

    }

    private fun calculateAnchorPointPostScale(
        trans: Float,
        scale: Float,
        endScale: Float,
        viewSize: Float,
        fieldSize: Float,
        midPinchZoom: Float
    ): Float {

        val estimatedTrans = -(endScale * trans)

        return if (endScale > 1f || maxScaleFactor <= 1f) {
            //Если поле будет увеличиваться

            if (isEnableStickyBounds()) {

                val contentSize = scale * fieldSize
                val offset = when (matrixScaleType) {
                    MatrixScaleType.FIT_START -> 0f
                    MatrixScaleType.FIT_END -> viewSize - contentSize * endScale
                    else -> (viewSize - contentSize * endScale) / 2f
                }

                (estimatedTrans + offset) / (1f - endScale)

            } else {
                midPinchZoom
            }

        } else {
            //Если поле будет уменьшаться
            if (isEnableStickyBounds()) {

                val transEnd = midPinchZoom * (1f - endScale) - estimatedTrans
                val contentSize = scale * fieldSize
                val endContentSize = contentSize * endScale
                val reserveDistance = endContentSize - (abs(transEnd) + viewSize)

                when {
                    transEnd > 0f -> estimatedTrans / (1f - endScale)
                    reserveDistance < 0f -> {
                        (estimatedTrans + (transEnd - reserveDistance)) / (1f - endScale)
                    }
                    else -> midPinchZoom

                }

            } else {
                midPinchZoom
            }

        }

    }

    private fun invalidateAnimation() {
        currentSpringAnimationTransX?.cancel()
        currentSpringAnimationTransX = null

        currentSpringAnimationTransY?.cancel()
        currentSpringAnimationTransY = null

        currentSpringAnimationScale?.cancel()
        currentSpringAnimationScale = null

        currentSpringAnimationFlingX?.cancel()
        currentSpringAnimationFlingX = null

        currentSpringAnimationFlingY?.cancel()
        currentSpringAnimationFlingY = null
    }

    private fun setMatrix(matrix: Matrix) {
        if (fieldMatrix != matrix) {
            fieldMatrix.set(matrix)
        }
        invalidate()
    }

    private fun applyMatrix(matrix: Matrix) {
        setMatrix(matrix)
    }

    private fun isEnableTrans(): Boolean = true
    private fun isEnableScrolling(): Boolean = true
    private fun isEnableStickyBounds(): Boolean = true
    private fun isEnableZoom(): Boolean = true
    private fun isEnableFling(): Boolean = true

    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            if (isEnableFling()) {

                if (!isNeedCorrectionScale()) {
                    fieldMatrix.getValues(matrixValues)

                    if (roundFloat(matrixValues[Matrix.MSCALE_X], 3) != measureData.estimatedMinScaleFactorX ||
                        roundFloat(matrixValues[Matrix.MSCALE_Y], 3) != measureData.estimatedMinScaleFactorY
                    ) {

                        val widthField = matrixValues[Matrix.MSCALE_X] * normFieldWidth
                        val startTransX = matrixValues[Matrix.MTRANS_X]
                        val lefLimit = 0f
                        val rightLimit = -(widthField - viewWidth)

                        val heightField = matrixValues[Matrix.MSCALE_Y] * normFieldHeight
                        val topLimit = 0f
                        val bottomLimit = -(heightField - viewHeight)
                        val startTransY = matrixValues[Matrix.MTRANS_Y]

                        currentSpringAnimationFlingX = flingAnimationOf(setter = { value ->
                            matrixValues[Matrix.MTRANS_X] = value
                            fieldMatrix.setValues(matrixValues)
                            applyMatrix(fieldMatrix)
                        }, getter = {
                            fieldMatrix.setValues(matrixValues)
                            matrixValues[Matrix.MTRANS_X]
                        }).apply {
                            friction = 0.75f
                            setStartVelocity(velocityX)
                            setMaxValue(lefLimit.coerceAtLeast(startTransX))
                            setMinValue(rightLimit.coerceAtMost(startTransX))
                            addEndListener { animation, canceled, value, velocity ->
                                if (animation == currentSpringAnimationFlingX) {
                                    currentSpringAnimationFlingX = null

                                    if (!canceled) {
                                        autoAlignmentX()
                                    }
                                }
                            }
                        }

                        currentSpringAnimationFlingY = flingAnimationOf(setter = { value ->
                            matrixValues[Matrix.MTRANS_Y] = value
                            fieldMatrix.setValues(matrixValues)
                            applyMatrix(fieldMatrix)
                        }, getter = {
                            fieldMatrix.setValues(matrixValues)
                            matrixValues[Matrix.MTRANS_Y]
                        }).apply {
                            friction = 0.75f
                            setStartVelocity(velocityY)
                            setMaxValue(topLimit.coerceAtLeast(startTransY))
                            setMinValue(bottomLimit.coerceAtMost(startTransY))
                            addEndListener { animation, canceled, value, velocity ->
                                if (animation == currentSpringAnimationFlingY) {
                                    currentSpringAnimationFlingY = null

                                    if (!canceled) {
                                        autoAlignmentY()
                                    }
                                }
                            }
                        }

                        currentSpringAnimationFlingX?.start()
                        currentSpringAnimationFlingY?.start()

                        return true

                    }
                }
            }

            return false
        }


    }
}

