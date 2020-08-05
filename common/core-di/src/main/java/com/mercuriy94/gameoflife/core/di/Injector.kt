package com.mercuriy94.gameoflife.core.di

/**
 * @author Nikita Marsyukov
 */
interface Injector<in T> {
    fun inject(target: T)
}