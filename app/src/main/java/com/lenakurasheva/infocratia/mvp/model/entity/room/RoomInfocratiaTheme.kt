package com.lenakurasheva.infocratia.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomInfocratiaTheme (
    @PrimaryKey var id: String,
    var name: String,
    var about: String,
    var creationDate: String,
    var groupId: String
    )