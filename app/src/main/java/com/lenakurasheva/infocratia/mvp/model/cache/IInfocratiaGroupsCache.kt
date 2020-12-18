package com.lenakurasheva.infocratia.mvp.model.cache

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IInfocratiaGroupsCache {
    fun putGroups(groups: List<InfocratiaGroup>): Completable
    fun getGroups(): Single<List<InfocratiaGroup>>
}