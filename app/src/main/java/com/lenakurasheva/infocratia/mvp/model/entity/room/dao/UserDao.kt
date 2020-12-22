package com.lenakurasheva.infocratia.mvp.model.entity.room.dao

import androidx.room.*
import com.lenakurasheva.infocratia.mvp.model.entity.room.RoomInfocratiaUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomInfocratiaUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomInfocratiaUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomInfocratiaUser>)

    @Update
    fun update(user: RoomInfocratiaUser)

    @Update
    fun update(vararg users: RoomInfocratiaUser)

    @Update
    fun update(users: List<RoomInfocratiaUser>)

    @Delete
    fun delete(user: RoomInfocratiaUser)

    @Delete
    fun delete(vararg users: RoomInfocratiaUser)

    @Delete
    fun delete(users: List<RoomInfocratiaUser>)

    @Query("DELETE FROM RoomInfocratiaUser")
    fun deleteAll()

    @Query("SELECT * FROM RoomInfocratiaUser")
    fun getAll(): List<RoomInfocratiaUser>?

    @Query("SELECT * FROM RoomInfocratiaUser WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): RoomInfocratiaUser?
}