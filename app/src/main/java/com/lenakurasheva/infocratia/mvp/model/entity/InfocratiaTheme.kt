package com.lenakurasheva.infocratia.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfocratiaTheme (
    @Expose val id: String,
    @Expose val name: String,
    @Expose val about: String?,
    @Expose val groupId: String?,
    @Expose val pubDate: String
): Parcelable

