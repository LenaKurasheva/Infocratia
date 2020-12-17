package com.lenakurasheva.infocratia.mvp.presenter.list

import com.lenakurasheva.infocratia.mvp.view.list.IItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}