package com.mercuriy94.gameoflife.widget.life.base;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;


public class ScaleTransform implements Transformer {

    private float mOldDist = 1f;

    @Override
    public Matrix onTouch(MotionEvent motionEvent,
                          Matrix imageMatrix,
                          PointF startTouchPoint,
                          PointF lastTouchPoint,
                          PointF currentTouchPoint,
                          final MeasureData measureData) {
        return imageMatrix;
    }

    @Override
    public Matrix onMultiTouch(MotionEvent motionEvent,
                               Matrix imageMatrix,
                               PointF startTouchPoint,
                               PointF lastTouchPoint,
                               PointF currentTouchPoint,
                               MeasureData measureData,
                               PointF midPoint) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_POINTER_DOWN: {
                mOldDist = computeSpacing(motionEvent);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float newDist = computeSpacing(motionEvent);
                if (newDist > 10f) {
                    float scale = (newDist / mOldDist);
                    imageMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
                }
                mOldDist = newDist;
                break;
            }

        }

        return imageMatrix;
    }

    float computeSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

}
