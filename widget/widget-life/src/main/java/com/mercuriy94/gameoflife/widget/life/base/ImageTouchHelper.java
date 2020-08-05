package com.mercuriy94.gameoflife.widget.life.base;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ImageTouchHelper {

    private Matrix mImageMatrix;
    private Map<TransformType, Transformer> mTransforms;
    private WeakReference<OnTouchTransformListener> mListenerWeakReference;
    @NonNull
    private PointF mStartTouchPoint;
    @NonNull
    private PointF mLastTouchPoint;
    @NonNull
    private PointF mCurrentTouchPoint;
    @NonNull
    private PointF mPivotPoint;

    private List<Integer> mTouchPointIdList = new LinkedList<>();

    public ImageTouchHelper(OnTouchTransformListener onTouchTransformListener) {
        mListenerWeakReference = new WeakReference<>(onTouchTransformListener);
        mTransforms = new HashMap<>();
        mTransforms.put(TransformType.TRANSLATION, new TranslationTransform());
        mTransforms.put(TransformType.SCALE, new ScaleTransform());
        mTransforms.put(TransformType.ROTATE, new RotateTransform());
        mStartTouchPoint = new PointF();
        mLastTouchPoint = new PointF();
        mCurrentTouchPoint = new PointF();
        mPivotPoint = new PointF();
        mImageMatrix = new Matrix();
    }

    public boolean onTouch(MotionEvent motionEvent,
                           final Matrix imageMatrix,
                           final MeasureData measureData) {
        this.mImageMatrix.set(imageMatrix);

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mTouchPointIdList.add(motionEvent.getPointerId(motionEvent.getActionIndex()));
                mStartTouchPoint.set(motionEvent.getX(motionEvent.getActionIndex()),
                        motionEvent.getY(motionEvent.getActionIndex()));
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                mTouchPointIdList.add(motionEvent.getPointerId(motionEvent.getActionIndex()));
                if (computeSpacing(motionEvent) > 10f) midPoint(mPivotPoint, motionEvent);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                mTouchPointIdList.remove(Integer.valueOf(motionEvent.getPointerId(motionEvent.getActionIndex())));
            }
        }

        if (mTouchPointIdList.isEmpty()) return false;
        int pointerId = mTouchPointIdList.get(0);


        mCurrentTouchPoint.set(motionEvent.getX(motionEvent.findPointerIndex(pointerId)),
                motionEvent.getY(motionEvent.findPointerIndex(pointerId)));

        Matrix matrix = mImageMatrix;

        if (motionEvent.getPointerCount() > 1) {
            for (Map.Entry<TransformType, Transformer> entry : mTransforms.entrySet()) {
                matrix = entry.getValue().onMultiTouch(motionEvent,
                        matrix,
                        mStartTouchPoint,
                        mLastTouchPoint,
                        mCurrentTouchPoint,
                        measureData,
                        mPivotPoint);
            }

        } else {
            for (Map.Entry<TransformType, Transformer> entry : mTransforms.entrySet()) {
                matrix = entry.getValue().onTouch(motionEvent,
                        matrix,
                        mStartTouchPoint,
                        mLastTouchPoint,
                        mCurrentTouchPoint,
                        measureData);
            }
        }

        if (mListenerWeakReference != null && mListenerWeakReference.get() != null) {
            mListenerWeakReference.get().onApplyMatrix(matrix);
        }

        mLastTouchPoint.set(mCurrentTouchPoint);
        return true;
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    float computeSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


}
