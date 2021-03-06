package com.lenakurasheva.infocratia.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Singleton

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun cicerone(): Cicerone<Router> = cicerone

    @Provides
    fun navigatorHolder() = cicerone.navigatorHolder

    @Provides
    fun router() = cicerone.router

}