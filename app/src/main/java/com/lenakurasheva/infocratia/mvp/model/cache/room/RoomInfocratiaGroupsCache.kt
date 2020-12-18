package com.lenakurasheva.infocratia.mvp.model.cache.room

import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaGroupsCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomInfocratiaGroupsCache(val db: Database): IInfocratiaGroupsCache {
    override fun putGroups(groups: List<InfocratiaGroup>): Completable {
        return Completable.fromAction() {
            val roomGroups = groups.map { group ->
                RoomInfocratiaGroup(
                    group.id ?: "",
                    group.name ?: "",
                    group.about ?: "",
                    group.image ?: "",
                    group.creationDate ?: "",

                )
            }
            db.groupDao.insert(roomGroups)
        }
    }

    override fun getGroups(): Single<List<InfocratiaGroup>> {
        return Single.fromCallable {
            db.groupDao.getAll().map { roomGroup ->
                InfocratiaGroup(roomGroup.id, roomGroup.name, roomGroup.about, roomGroup.image, roomGroup.creationDate)
            }
        }
    }
}