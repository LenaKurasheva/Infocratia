package com.lenakurasheva.infocratia.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomInfocratiaGroup (
    @PrimaryKey var id: String,
    var name: String,
    var about: String,
    var image: String,
    var creationDate: String
    )