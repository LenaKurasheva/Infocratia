package com.lenakurasheva.infocratia.di

import com.lenakurasheva.infocratia.di.modules.*
import com.lenakurasheva.infocratia.mvp.presenter.GroupsPresenter
import com.lenakurasheva.infocratia.mvp.presenter.MainPresenter
import com.lenakurasheva.infocratia.ui.activity.MainActivity
import com.lenakurasheva.infocratia.ui.adapter.GroupsRvAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NavigationModule::class,
    GroupsModule::class,
    ApiModule::class,
    ImageLoaderModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(groupsPresenter: GroupsPresenter)
    fun inject(groupsRvAdapter: GroupsRvAdapter)
}