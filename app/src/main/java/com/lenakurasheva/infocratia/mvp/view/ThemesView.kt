package com.lenakurasheva.infocratia.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ThemesView: MvpView {
    fun init()
    fun updateThemesList()
    fun signIn()
    fun signOut()
    fun allThemesPressed()
    fun myThemesPressed()
    fun myThemesIsNotPressed()
    fun allThemesIsNotPressed()
}