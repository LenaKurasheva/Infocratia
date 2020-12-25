package com.lenakurasheva.infocratia.mvp.view

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface MainView: MvpView{
    @Skip
    fun openSignInIntent()

    fun signOut()
    fun signIn()

    fun setGroupsMenuItemChecked()
    fun setThemesMenuItemChecked()
}