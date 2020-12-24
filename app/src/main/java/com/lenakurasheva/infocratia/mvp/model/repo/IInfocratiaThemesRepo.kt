package com.lenakurasheva.infocratia.mvp.model.repo

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import io.reactivex.rxjava3.core.Single

interface IInfocratiaThemesRepo {
    fun getAllThemes(): Single<List<InfocratiaTheme>>
    fun getUserThemes(): Single<List<InfocratiaTheme>>
}