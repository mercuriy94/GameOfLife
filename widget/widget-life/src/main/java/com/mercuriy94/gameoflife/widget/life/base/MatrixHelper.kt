package com.mercuriy94.gameoflife.widget.life.base

import android.graphics.Matrix
import android.util.Log

private const val TAG = "MatrixHelper"

fun logTransFromMatrix(matrix: Matrix) {
    val values = FloatArray(9)
    matrix.getValues(values)
    Log.d(TAG, "trans x = ${values[Matrix.MTRANS_X]}, trans y = ${values[Matrix.MTRANS_Y]}")
}

fun logMatrixValues(matrix: Matrix) {
    val values = FloatArray(9)
    matrix.getValues(values)
    Log.d(
        TAG, String.format(
            "Matrix values:\n" +
                    "scaleX = %f  skewX = %f transX = %f\n" +
                    "skewY = %f scaleY = %f transY = %f\n" +
                    "persp_0 = %f persp_1 = %f persp_2 = %f",
            values[Matrix.MSCALE_X], values[Matrix.MSKEW_X], values[Matrix.MTRANS_X],
            values[Matrix.MSKEW_Y], values[Matrix.MSCALE_Y], values[Matrix.MTRANS_Y],
            values[Matrix.MPERSP_0], values[Matrix.MPERSP_1], values[Matrix.MPERSP_2]
        )
    )
}