package com.lenakurasheva.infocratia.di.themes

import com.lenakurasheva.infocratia.di.ThemesScope
import com.lenakurasheva.infocratia.di.themes.module.ThemesModule
import com.lenakurasheva.infocratia.mvp.presenter.ThemesPresenter
import dagger.Subcomponent

@ThemesScope
@Subcomponent( modules = [ThemesModule::class])

interface ThemesSubcomponent {
    fun inject(themesPresenter: ThemesPresenter)
}