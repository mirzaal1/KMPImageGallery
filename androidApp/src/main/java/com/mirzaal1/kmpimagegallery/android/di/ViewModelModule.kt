package com.mirzaal1.kmpimagegallery.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ui.viewmodel.ImagesGalleryViewModel

val viewModelModule = module {
    viewModel { ImagesGalleryViewModel(get()) }
}
