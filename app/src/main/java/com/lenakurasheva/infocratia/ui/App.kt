package com.lenakurasheva.infocratia.ui

import android.app.Application
import com.lenakurasheva.infocratia.di.AppComponent
import com.lenakurasheva.infocratia.di.DaggerAppComponent
import com.lenakurasheva.infocratia.di.modules.AppModule

class App: Application() {
    companion object {
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =  DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}