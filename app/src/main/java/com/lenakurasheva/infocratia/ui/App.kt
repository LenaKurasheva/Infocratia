package com.lenakurasheva.infocratia.ui

import android.app.Application
import com.lenakurasheva.infocratia.di.AppComponent
import com.lenakurasheva.infocratia.di.DaggerAppComponent
import com.lenakurasheva.infocratia.di.groups.GroupsSubcomponent
import com.lenakurasheva.infocratia.di.groups.IGroupsScopeContainer
import com.lenakurasheva.infocratia.di.modules.AppModule
import com.lenakurasheva.infocratia.di.themes.IThemesScopeContainer
import com.lenakurasheva.infocratia.di.themes.ThemesSubcomponent

class App: Application(), IGroupsScopeContainer, IThemesScopeContainer {
    companion object {
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent
        private set

    var groupsSubcomponent: GroupsSubcomponent? = null
        private set

    var themesSubcomponent: ThemesSubcomponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =  DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initGroupsComponent() = appComponent.groupsSubcomponent().also {
        groupsSubcomponent = it
    }

    fun initThemesComponent() = appComponent.themesSubcomponent().also {
        themesSubcomponent = it
    }

    override fun releaseGroupsScope() {
        groupsSubcomponent = null
    }

    override fun releaseThemesScope() {
        themesSubcomponent = null
    }

}