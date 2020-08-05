package com.mercuriy94.gameoflife.widget.life.base;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

public class TranslationTransform implements Transformer {

    @Override
    public Matrix onTouch(
            MotionEvent motionEvent,
            Matrix imageMatrix,
            PointF startTouchPoint,
            PointF lastTouchPoint,
            PointF currentTouchPoint,
            final MeasureData measureData) {

        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
            float dx = motionEvent.getX() - lastTouchPoint.x;
            float dy = motionEvent.getY() - lastTouchPoint.y;


            imageMatrix.postTranslate(dx, dy);
        }

        return imageMatrix;
    }

    @Override
    public Matrix onMultiTouch(
            MotionEvent motionEvent,
            Matrix imageMatrix,
            PointF startTouchPoint,
            PointF lastTouchPoint,
            PointF currentTouchPoint,
            MeasureData measureData,
            PointF midPoint) {

        //do nothing

        return imageMatrix;
    }

}
