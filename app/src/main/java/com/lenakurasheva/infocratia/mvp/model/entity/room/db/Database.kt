package com.lenakurasheva.infocratia.mvp.model.entity.room.db

import androidx.room.RoomDatabase
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.entity.room.dao.GroupDao
import com.lenakurasheva.infocratia.mvp.model.entity.room.dao.ThemeDao
import com.lenakurasheva.infocratia.mvp.model.entity.room.dao.UserDao

@androidx.room.Database(entities = [RoomInfocratiaGroup::class, RoomInfocratiaTheme::class, RoomInfocratiaUser::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract val groupDao: GroupDao
    abstract val themeDao: ThemeDao
    abstract val userDao: UserDao

    companion object {
        const val DB_NAME = "database.db"
        private var instance: Database? = null

    }
}