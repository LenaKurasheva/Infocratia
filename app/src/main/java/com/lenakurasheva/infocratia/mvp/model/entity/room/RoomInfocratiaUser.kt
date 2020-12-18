package com.lenakurasheva.infocratia.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomInfocratiaUser (
    @PrimaryKey val id: String,
    val login: String,
    val email: String,
    val userImage: String?
    )