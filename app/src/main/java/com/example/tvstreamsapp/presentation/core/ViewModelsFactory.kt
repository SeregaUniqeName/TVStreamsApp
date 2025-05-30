package com.example.tvstreamsapp.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelsFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}