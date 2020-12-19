package com.lenakurasheva.infocratia.di.modules

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.cache.room.RoomInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ThemesModule {

    @Singleton
    @Provides
    fun themesCache(database: Database): IInfocratiaThemesCache = RoomInfocratiaThemesCache(database)

    @Singleton
    @Provides
    fun themesRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IInfocratiaThemesCache): IInfocratiaThemesRepo = RetrofitInfocratiaThemesRepo(api, networkStatus, cache)
}