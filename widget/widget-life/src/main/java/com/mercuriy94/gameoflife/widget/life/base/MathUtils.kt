package com.mercuriy94.gameoflife.widget.life.base

/**
 * @author Nikita Marsyukov
 */
const val ROUND_DEFAULT_SCALE_FLOAT: Int = 5

/**
 * Округление чисел с плавающией точкой
 *
 * @param value значение для округления;
 * @param scale кол-во цифр для округления;
 * @return округлённое число;
 */
fun roundFloat(value: Float, scale: Int): Float {
    var pow = 10
    for (i in 1 until scale) pow *= 10
    val tmp = value * pow
    return ((if (tmp - tmp.toInt() >= 0.5f) tmp + 1 else tmp).toInt()).toFloat() / pow
}
