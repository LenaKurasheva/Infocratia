package com.lenakurasheva.infocratia.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InfocratiaUser (
    @Expose val id: String?,
    @Expose val login: String?,
    @Expose val email: String?,
    @Expose val userImage: String?,
    @Expose val accessToken: String?
): Parcelable