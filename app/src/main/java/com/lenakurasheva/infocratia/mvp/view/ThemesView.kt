package com.lenakurasheva.infocratia.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ThemesView: MvpView {
    fun init()
    fun updateThemesList()
}