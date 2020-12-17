package com.lenakurasheva.infocratia.mvp.view.list

interface GroupItemView: IItemView {
    fun setTitle(text: String)
    fun setDescription(text: String)
    fun loadImage(url: String)
}