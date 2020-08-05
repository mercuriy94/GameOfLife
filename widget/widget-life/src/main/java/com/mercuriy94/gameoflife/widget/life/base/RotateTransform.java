package com.mercuriy94.gameoflife.widget.life.base;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

public class RotateTransform implements Transformer {

    private float newRot = 0f;
    private float d = 0f;

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
                d = rotation(motionEvent);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (motionEvent.getPointerCount() == 2) {
                    newRot = rotation(motionEvent);
                    float r = newRot - d;
                    imageMatrix.postRotate(r, midPoint.x, midPoint.y);
                    d = rotation(motionEvent);
                }
                break;
            }
        }

        return imageMatrix;
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
