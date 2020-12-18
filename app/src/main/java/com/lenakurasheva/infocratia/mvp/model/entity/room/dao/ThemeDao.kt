package com.lenakurasheva.infocratia.mvp.model.entity.room.dao

import androidx.room.*
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaTheme

@Dao
interface ThemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(theme: RoomInfocratiaTheme)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg themes: RoomInfocratiaTheme)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(themes: List<RoomInfocratiaTheme>)

    @Update
    fun update(theme: RoomInfocratiaTheme)

    @Update
    fun update(vararg themes: RoomInfocratiaTheme)

    @Update
    fun update(themes: List<RoomInfocratiaTheme>)

    @Delete
    fun delete(theme: RoomInfocratiaTheme)

    @Delete
    fun delete(vararg themes: RoomInfocratiaTheme)

    @Delete
    fun delete(themes: List<RoomInfocratiaTheme>)

    @Query("SELECT * FROM RoomInfocratiaTheme")
    fun getAll(): List<RoomInfocratiaTheme>

    @Query("SELECT * FROM RoomInfocratiaTheme WHERE name = :name LIMIT 1")
    fun findByName(name: String): RoomInfocratiaTheme?
}