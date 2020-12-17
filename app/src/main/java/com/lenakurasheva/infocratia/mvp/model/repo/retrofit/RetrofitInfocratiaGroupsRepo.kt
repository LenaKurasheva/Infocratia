package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import io.reactivex.rxjava3.core.Single

class RetrofitInfocratiaGroupsRepo: IInfocratiaGroupsRepo {
    override fun getAllGroups(): Single<List<InfocratiaGroup>> {
       val grous: List<InfocratiaGroup> = mutableListOf(InfocratiaGroup("1", "first group", "111", null), InfocratiaGroup("2", "second group", "222", null) )
        return Single.just(grous)
    }

    override fun getGroupsUserSubscribedTo(user: InfocratiaUser): Single<List<InfocratiaGroup>> {
        TODO("Not yet implemented")
    }
}