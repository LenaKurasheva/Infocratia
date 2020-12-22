package com.lenakurasheva.infocratia.mvp.model.entity.room.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE RoomInfocratiaUser ADD COLUMN accessToken TEXT  DEFAULT('')")
    }
}