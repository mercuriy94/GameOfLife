package com.mercuriy94.gameoflife.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author Nikita Marsyukov
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
