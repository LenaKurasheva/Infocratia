package com.lenakurasheva.infocratia.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InfocratiaGroup (
    @Expose val name: String,
    @Expose val about: String?,
    @Expose val id: String,
    @Expose val image: String?

): Parcelable