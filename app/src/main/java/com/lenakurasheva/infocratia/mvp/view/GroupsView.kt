package com.lenakurasheva.infocratia.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface GroupsView: MvpView {
    fun init()
    fun updateGroupsList()
    fun signIn()
    fun signOut()
    fun allGroupsPressed()
    fun myGroupsPressed()
    fun myGroupsIsNotPressed()
    fun allGroupsIsNotPressed()

}