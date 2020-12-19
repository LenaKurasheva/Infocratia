package com.lenakurasheva.infocratia.mvp.model.cache.room

import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaThemesCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomInfocratiaThemesCache (val db: Database): IInfocratiaThemesCache {
    override fun putThemes(themes: List<InfocratiaTheme>): Completable {
        return Completable.fromAction() {
            val roomThemes = themes.map { theme ->
                RoomInfocratiaTheme(
                    theme.id ?: "",
                    theme.name ?: "",
                    theme.about ?: "",
                    theme.groupId ?: "",
                    theme.pubDate ?: "",
                )
            }
            db.themeDao.insert(roomThemes)
        }
    }

    override fun getThemes(): Single<List<InfocratiaTheme>> {
        return Single.fromCallable {
            db.themeDao.getAll().map { roomTheme ->
                InfocratiaTheme(
                    roomTheme.id,
                    roomTheme.name,
                    roomTheme.about,
                    roomTheme.groupId,
                    roomTheme.pubDate
                )
            }
        }
    }
}