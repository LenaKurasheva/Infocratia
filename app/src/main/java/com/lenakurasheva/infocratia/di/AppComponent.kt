package com.lenakurasheva.infocratia.di

import com.lenakurasheva.infocratia.di.groups.GroupsSubcomponent
import com.lenakurasheva.infocratia.di.groups.module.GroupsModule
import com.lenakurasheva.infocratia.di.modules.*
import com.lenakurasheva.infocratia.di.themes.ThemesSubcomponent
import com.lenakurasheva.infocratia.di.themes.module.ThemesModule
import com.lenakurasheva.infocratia.mvp.presenter.GroupPresenter
import com.lenakurasheva.infocratia.mvp.presenter.GroupsPresenter
import com.lenakurasheva.infocratia.mvp.presenter.MainPresenter
import com.lenakurasheva.infocratia.mvp.presenter.ThemesPresenter
import com.lenakurasheva.infocratia.ui.activity.MainActivity
import com.lenakurasheva.infocratia.ui.adapter.GroupsRvAdapter
import com.lenakurasheva.infocratia.ui.auth.AndroidAuth
import com.lenakurasheva.infocratia.ui.fragment.GroupFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NavigationModule::class,
    ApiModule::class,
    ImageLoaderModule::class,
    AuthModule::class
])
interface AppComponent {
    fun groupsSubcomponent(): GroupsSubcomponent
    fun themesSubcomponent(): ThemesSubcomponent

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(androidAuth: AndroidAuth)
}