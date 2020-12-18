package com.lenakurasheva.infocratia.di.modules

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import com.lenakurasheva.infocratia.ui.network.AndroidNetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GroupsModule {

    @Singleton
    @Provides
    fun groupsRepo(api: IDataSource, networkStatus: INetworkStatus): IInfocratiaGroupsRepo = RetrofitInfocratiaGroupsRepo(api, networkStatus)
}