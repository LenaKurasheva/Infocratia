package com.lenakurasheva.infocratia.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface GroupView: MvpView {
    fun init()
    fun setName(name: String)
    fun setAbout(about: String)
    fun setCreationDate(creationDate: String)
    fun loadImage(image: String)
}