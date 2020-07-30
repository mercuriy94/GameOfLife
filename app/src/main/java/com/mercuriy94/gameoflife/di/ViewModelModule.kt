package com.mercuriy94.gameoflife.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * Модуль предназаныченный для предоставления фабрики, которая создает клиентские [androidx.lifecycle.ViewModel]
 *
 * @author Nikita Marsyukov
 */
@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(
        viewModelProviders: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelModelFactory = ViewModelModelFactory(viewModelProviders)

    class ViewModelModelFactory(
        private val viewModelProviders: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModelProvider = viewModelProviders[modelClass]
            return viewModelProvider!!.get() as T
        }
    }

}
