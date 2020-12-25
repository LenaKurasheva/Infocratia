package com.lenakurasheva.infocratia.di.themes.module

import com.lenakurasheva.infocratia.di.ThemesScope
import com.lenakurasheva.infocratia.di.themes.IThemesScopeContainer
import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.cache.room.RoomInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import com.lenakurasheva.infocratia.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class ThemesModule {

    @ThemesScope
    @Provides
    fun themesCache(database: Database): IInfocratiaThemesCache = RoomInfocratiaThemesCache(database)

    @Provides
    fun themesRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IInfocratiaThemesCache,
                   @Named("authToken") authToken: String): IInfocratiaThemesRepo =
        RetrofitInfocratiaThemesRepo(api, networkStatus, cache, authToken)

    @ThemesScope
    @Provides
    fun scopeContainer(app: App): IThemesScopeContainer = app
}