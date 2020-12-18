package com.lenakurasheva.infocratia.di.modules

import android.widget.ImageView
import com.lenakurasheva.infocratia.mvp.model.image.IImageLoader
import com.lenakurasheva.infocratia.ui.image.GlideImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoaderModule {

    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> = GlideImageLoader()
}