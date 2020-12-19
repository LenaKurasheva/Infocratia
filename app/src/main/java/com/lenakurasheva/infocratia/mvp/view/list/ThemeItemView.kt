package com.lenakurasheva.infocratia.mvp.view.list

interface ThemeItemView: IItemView {
    fun setTitle(text: String)
    fun setDescription(text: String)
    fun setPubDate(text: String)
}