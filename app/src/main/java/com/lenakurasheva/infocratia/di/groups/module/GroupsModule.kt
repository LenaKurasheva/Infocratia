package com.lenakurasheva.infocratia.di.groups.module

import com.lenakurasheva.infocratia.di.GroupsScope
import com.lenakurasheva.infocratia.di.groups.IGroupsScopeContainer
import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.cache.room.RoomInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import com.lenakurasheva.infocratia.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class GroupsModule {

    @GroupsScope
    @Provides
    fun groupsCache(database: Database): IInfocratiaGroupsCache = RoomInfocratiaGroupsCache(database)

    @Provides
    fun groupsRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IInfocratiaGroupsCache,
                   @Named("authToken") authToken: String): IInfocratiaGroupsRepo =
        RetrofitInfocratiaGroupsRepo(api, networkStatus, cache, authToken)

    @GroupsScope
    @Provides
    fun scopeContainer(app: App): IGroupsScopeContainer = app
}