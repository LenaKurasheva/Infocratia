package com.lenakurasheva.infocratia.di.modules

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.cache.room.RoomInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import com.lenakurasheva.infocratia.ui.network.AndroidNetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class GroupsModule {

    @Singleton
    @Provides
    fun groupsCache(database: Database): IInfocratiaGroupsCache = RoomInfocratiaGroupsCache(database)

    @Singleton
    @Provides
    fun groupsRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IInfocratiaGroupsCache): IInfocratiaGroupsRepo = RetrofitInfocratiaGroupsRepo(api, networkStatus, cache)
}