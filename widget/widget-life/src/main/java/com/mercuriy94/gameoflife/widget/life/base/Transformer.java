package com.mercuriy94.gameoflife.widget.life.base;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

public interface Transformer {

    Matrix onTouch(MotionEvent motionEvent,
                   Matrix imageMatrix,
                   PointF startTouchPoint,
                   PointF lastTouchPoint,
                   PointF currentTouchPoint,
                   final MeasureData measureData);

    Matrix onMultiTouch(MotionEvent motionEvent,
                        Matrix imageMatrix,
                        PointF startTouchPoint,
                        PointF lastTouchPoint,
                        PointF currentTouchPoint,
                        final MeasureData measureData,
                        PointF midPoint);

}
