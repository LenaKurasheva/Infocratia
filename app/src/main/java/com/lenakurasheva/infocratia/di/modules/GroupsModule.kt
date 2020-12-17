package com.lenakurasheva.infocratia.di.modules

import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaGroupsRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GroupsModule {

    @Singleton
    @Provides
    fun gropsRepo(): IInfocratiaGroupsRepo = RetrofitInfocratiaGroupsRepo()
}