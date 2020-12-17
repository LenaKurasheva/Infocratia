package com.lenakurasheva.infocratia.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfocratiaTheme (
    @Expose val title: String,
    @Expose val description: String,
    @Expose val id: String,
    @Expose val body: String,
    @Expose val themeImage: String,
    @Expose val group: InfocratiaGroup?
): Parcelable

