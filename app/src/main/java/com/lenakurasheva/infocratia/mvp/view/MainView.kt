package com.lenakurasheva.infocratia.mvp.view

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MainView: MvpView{
    fun openSignInIntent()
    fun signOut()
}