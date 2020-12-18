package com.lenakurasheva.infocratia.di.modules

import androidx.room.Room
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.ui.App
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Provides
    fun app(): App{
        return app
    }
}