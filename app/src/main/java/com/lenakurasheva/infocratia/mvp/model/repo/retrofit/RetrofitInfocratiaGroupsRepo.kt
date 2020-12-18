package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitInfocratiaGroupsRepo(val api: IDataSource, val networkStatus: INetworkStatus): IInfocratiaGroupsRepo {

    override fun getAllGroups() = networkStatus.inOnlineSingle().flatMap { isOnline ->
//        if (isOnline) {
            api.getAllGroups().flatMap { groups ->
//                cache.putUsers(users).andThen(Single.just(users))
                Single.just(groups)
            }
//        } else {
//            cache.getUsers()
//        }
    }.subscribeOn(Schedulers.io())

    override fun getGroupsUserSubscribedTo(user: InfocratiaUser): Single<List<InfocratiaGroup>> {
        TODO("Not yet implemented")
    }
}