package com.lenakurasheva.infocratia.di

import com.lenakurasheva.infocratia.di.modules.AppModule
import com.lenakurasheva.infocratia.di.modules.GroupsModule
import com.lenakurasheva.infocratia.di.modules.NavigationModule
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
    GroupsModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(groupsPresenter: GroupsPresenter)
    abstract fun inject(groupsRvAdapter: GroupsRvAdapter)
}