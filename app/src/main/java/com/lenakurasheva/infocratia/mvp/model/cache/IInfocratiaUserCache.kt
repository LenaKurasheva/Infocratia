package com.lenakurasheva.infocratia.mvp.model.cache

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IInfocratiaUserCache {
    fun putUser(user: InfocratiaUser): Completable
    fun getUser(): Single<InfocratiaUser?>
    fun deleteUser(): Completable
}