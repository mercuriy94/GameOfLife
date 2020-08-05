package com.mercuriy94.gameoflife.widget.life.base;


import android.graphics.Matrix;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

public enum MatrixScaleType {

    FIT_START {
        @NonNull
        @Override
        public MeasureData measure(@NonNull MeasureParams measureParams) {

            MeasureData measureData = MatrixScaleType.computeBaseMeasureData(measureParams);
            measureData.scaleX = measureData.scaleY =
                    Math.min(measureData.measuredWidth / measureParams.getContentWidth(),
                            measureData.measuredHeight / measureParams.getContentHeight());


            measureData.estimatedMinScaleX = measureData.scaleX * measureParams.getMinScaleFactor();
            measureData.estimatedMinScaleY = measureData.scaleY * measureParams.getMinScaleFactor();

            return finishComputeMeasure(measureData, measureParams);
        }
    },

    FIT_CENTER {
        @NonNull
        @Override
        public MeasureData measure(@NonNull MeasureParams measureParams) {
            MeasureData measureData = MatrixScaleType.computeBaseMeasureData(measureParams);

            measureData.scaleX = measureData.scaleY =
                    Math.min(measureData.measuredWidth / measureParams.getContentWidth(),
                            measureData.measuredHeight / measureParams.getContentHeight());

            measureData.estimatedMinScaleX = measureData.scaleX * measureParams.getMinScaleFactor();
            measureData.estimatedMinScaleY = measureData.scaleY * measureParams.getMinScaleFactor();

            measureData.transX = (measureData.measuredWidth -
                    (measureParams.getContentWidth() * measureData.estimatedMinScaleX)) / 2f;

            measureData.transY = (measureData.measuredHeight -
                    (measureParams.getContentHeight() * measureData.estimatedMinScaleX)) / 2f;

            return finishComputeMeasure(measureData, measureParams);
        }
    },


    CENTER_INSIDE {
        @NonNull
        @Override
        public MeasureData measure(@NonNull MeasureParams measureParams) {
            MeasureData measureData = MatrixScaleType.computeBaseMeasureData(measureParams);

            measureData.scaleX = measureData.scaleY =
                    Math.min(1f, Math.min(measureData.measuredWidth / measureParams.getContentWidth(),
                            measureData.measuredHeight / measureParams.getContentHeight()));

            measureData.estimatedMinScaleX = measureData.scaleX * measureParams.getMinScaleFactor();
            measureData.estimatedMinScaleY = measureData.scaleY * measureParams.getMinScaleFactor();

            measureData.transX = (measureData.measuredWidth -
                    (measureParams.getContentWidth() * measureData.estimatedMinScaleX)) / 2f;

            measureData.transY = (measureData.measuredHeight -
                    (measureParams.getContentHeight() * measureData.estimatedMinScaleX)) / 2f;

            return finishComputeMeasure(measureData, measureParams);
        }
    };

    public abstract @NonNull
    MeasureData measure(@NonNull MeasureParams measureParams);

    @NonNull
    private static MeasureData finishComputeMeasure(@NonNull MeasureData measureData, @NonNull MeasureParams measureParams) {

        measureData.estimatedMaxScaleX =
                MathUtilsKt.roundFloat(measureData.scaleX * measureParams.getMaxScaleFactor(),
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        measureData.estimatedMaxScaleY =
                MathUtilsKt.roundFloat(measureData.scaleY * measureParams.getMaxScaleFactor(),
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        measureData.estimatedMidScaleX =
                MathUtilsKt.roundFloat(measureData.scaleX * measureParams.getMidScaleFactor(),
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        measureData.estimatedMidScaleY =
                MathUtilsKt.roundFloat(measureData.scaleY * measureParams.getMidScaleFactor(),
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        measureData.estimatedMinScaleX =
                MathUtilsKt.roundFloat(measureData.estimatedMinScaleX,
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        measureData.estimatedMinScaleY =
                MathUtilsKt.roundFloat(measureData.estimatedMinScaleY,
                        MathUtilsKt.ROUND_DEFAULT_SCALE_FLOAT);

        return measureData;
    }

    private static MeasureData computeBaseMeasureData(@NonNull MeasureParams measureParams) {
        MeasureData measureData = new MeasureData();

        measureData.widthMode = View.MeasureSpec.getMode(measureParams.getWidthMeasureSpec());
        int viewWidth = View.MeasureSpec.getSize(measureParams.getWidthMeasureSpec());

        measureData.heightMode = View.MeasureSpec.getMode(measureParams.getHeightMeasureSpec());
        int viewHeight = View.MeasureSpec.getSize(measureParams.getHeightMeasureSpec());

        if (measureData.heightMode == View.MeasureSpec.EXACTLY) {
            measureData.measuredHeight = viewHeight;
        } else if (measureData.heightMode == View.MeasureSpec.UNSPECIFIED) {
            measureData.measuredHeight = measureParams.getContentHeight();
        } else {
            measureData.measuredHeight = Math.min(measureParams.getContentHeight(), viewHeight);
        }

        if (measureData.widthMode == View.MeasureSpec.EXACTLY) {
            measureData.measuredWidth = viewWidth;
        } else if (measureData.widthMode == View.MeasureSpec.UNSPECIFIED) {
            measureData.measuredWidth = measureParams.getContentWidth();
        } else {
            measureData.measuredWidth = Math.min(measureParams.getContentWidth(), viewWidth);
        }

        return measureData;
    }

    public static void printMatrixValues(Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        Log.i("Matrix", String.format("Matrix values:\n" +
                        "%f %f %f\n" +
                        "%f %f %f\n" +
                        "%f %f %f",
                values[Matrix.MSCALE_X], values[Matrix.MSKEW_X], values[Matrix.MTRANS_X],
                values[Matrix.MSKEW_Y], values[Matrix.MSCALE_Y], values[Matrix.MTRANS_Y],
                values[Matrix.MPERSP_0], values[Matrix.MPERSP_1], values[Matrix.MPERSP_2]));
    }

}