package com.lenakurasheva.infocratia.mvp.model.entity.room.dao

import androidx.room.*
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaGroup

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: RoomInfocratiaGroup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg groups: RoomInfocratiaGroup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(groups: List<RoomInfocratiaGroup>)

    @Update
    fun update(group: RoomInfocratiaGroup)

    @Update
    fun update(vararg groups: RoomInfocratiaGroup)

    @Update
    fun update(groups: List<RoomInfocratiaGroup>)

    @Delete
    fun delete(group: RoomInfocratiaGroup)

    @Delete
    fun delete(vararg groups: RoomInfocratiaGroup)

    @Delete
    fun delete(groups: List<RoomInfocratiaGroup>)

    @Query("SELECT * FROM RoomInfocratiaGroup")
    fun getAll(): List<RoomInfocratiaGroup>

    @Query("SELECT * FROM RoomInfocratiaGroup WHERE name = :name LIMIT 1")
    fun findByName(name: String): RoomInfocratiaGroup?
}