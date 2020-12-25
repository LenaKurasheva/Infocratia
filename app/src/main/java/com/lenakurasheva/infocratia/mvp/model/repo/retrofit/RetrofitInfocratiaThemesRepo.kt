package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitInfocratiaThemesRepo (val api: IDataSource, val networkStatus: INetworkStatus, val cache: IInfocratiaThemesCache, val authToken: String):
    IInfocratiaThemesRepo {

    override fun getAllThemes() = networkStatus.inOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            print("***IS ONLINE***")
            api.getAllThemes().flatMap { themes ->
                cache.putThemes(themes = themes).andThen(Single.just(themes))
            }
        } else {
            print("***IS OFFLINE***")
            cache.getThemes()
        }
    }.subscribeOn(Schedulers.io())

    override fun getUserThemes(): Single<List<InfocratiaTheme>> {
        //Here will be userThemesCache, backend is in dev (now api themes response doesn't include userId)
        return api.getUserThemes(authToken).subscribeOn(Schedulers.io())
    }
}