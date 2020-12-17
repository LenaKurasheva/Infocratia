package com.lenakurasheva.infocratia.di.modules

import com.lenakurasheva.infocratia.ui.App
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

@Module
class AppModule(val app: App) {

    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    fun app(): App{
        return app
    }
}