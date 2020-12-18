package com.lenakurasheva.infocratia.mvp.model.cache

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IInfocratiaThemesCache {
    fun putThemes(themes: List<InfocratiaTheme>): Completable
    fun getThemes(): Single<List<InfocratiaTheme>>
}