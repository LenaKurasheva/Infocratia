package com.lenakurasheva.infocratia.mvp.model.repo

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import io.reactivex.rxjava3.core.Single

interface IInfocratiaGroupsRepo {
    fun getAllGroups(): Single<List<InfocratiaGroup>>
    fun getGroupsUserSubscribedTo(user: InfocratiaUser): Single<List<InfocratiaGroup>>
}