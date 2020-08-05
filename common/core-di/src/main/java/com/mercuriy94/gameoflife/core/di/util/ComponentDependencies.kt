@file:JvmName("ComponentDependenciesResolver")

package com.mercuriy94.gameoflife.core.di.util

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

/***
 * Утилитный файл для поиска необходимых зависимостей в дереве фрагментов и активити
 *
 * @author Nikita Marsyukov
 */

interface ComponentDependencies

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T {
    return findDependenciesProvider()[T::class.java] as T
}

inline fun <reified T : ComponentDependencies> Activity.findComponentDependencies(): T {
    return findDependenciesProvider()[T::class.java] as T
}

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasComponentDependencies {
    val dependencies: ComponentDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

fun Fragment.findDependenciesProvider(): ComponentDependenciesProvider {
    var current: Fragment? = parentFragment
    while (current !is HasComponentDependencies?) {
        current = current?.parentFragment
    }

    val hasDaggerProviders = current ?: when {
        activity is HasComponentDependencies -> activity as HasComponentDependencies
        activity?.application is HasComponentDependencies -> activity?.application as HasComponentDependencies
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }
    return hasDaggerProviders.dependencies
}

fun Activity.findDependenciesProvider(): ComponentDependenciesProvider {
    val application = this.application
    check(application is HasComponentDependencies) {
        String.format(
            "%s does not implement %s",
            application.javaClass.canonicalName,
            HasComponentDependencies::class.java.canonicalName
        )
    }

    return application.dependencies
}
