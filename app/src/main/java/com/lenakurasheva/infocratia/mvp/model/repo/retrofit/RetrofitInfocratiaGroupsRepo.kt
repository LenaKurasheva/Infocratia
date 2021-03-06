package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitInfocratiaGroupsRepo(val api: IDataSource, val networkStatus: INetworkStatus, val cache: IInfocratiaGroupsCache, val authToken: String): IInfocratiaGroupsRepo {

    override fun getAllGroups() = networkStatus.inOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            print("***IS ONLINE***")
            api.getAllGroups().flatMap { groups ->
                cache.putGroups(groups).andThen(Single.just(groups))
            }
        } else {
            print("***IS OFFLINE***")
            cache.getGroups()
        }
    }.subscribeOn(Schedulers.io())

    override fun getUserGroups(): Single<List<InfocratiaGroup>> {
        //Here will be userGroupsCache, backend is in dev (now api groups response doesn't include userId)
        return api.getUserGroups(authToken).subscribeOn(Schedulers.io())
    }
}