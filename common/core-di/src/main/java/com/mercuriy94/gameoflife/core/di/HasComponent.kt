package com.mercuriy94.gameoflife.core.di

/**
 * @author Nikita Marsyukov
 */
interface HasComponent<out T> {
    val component : T
}