package com.lenakurasheva.infocratia.mvp.model.cache.room

import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomInfocratiaUserCache(val db: Database): IInfocratiaUserCache {
    override fun putUser(user: InfocratiaUser): Completable {
        return Completable.fromAction() {
           val roomUser = RoomInfocratiaUser(
                   user.id ?: "",
                   user.login ?: "",
                   user.email ?: "",
                   user.userImage ?: "",
                   user.accessToken ?: "")
            db.userDao.insert(roomUser)
        }.subscribeOn(Schedulers.io())
    }

    override fun getUser(): Single<InfocratiaUser?> {
        var roomUser: RoomInfocratiaUser? = null
        return Single.fromCallable {
            val users = db.userDao.getAll()
            users?.let { if(users.isNotEmpty()) roomUser = it.last() }
            roomUser?.let { val infocratisUser = InfocratiaUser(it.id, it.login, it.email, it.userImage,it.accessToken)
            infocratisUser } ?: InfocratiaUser("","","","","")
        }.subscribeOn(Schedulers.io())
    }

    override fun deleteUser(): Completable {
        return Completable.fromAction {
            db.userDao.deleteAll()
        }.subscribeOn(Schedulers.io())
    }
}