package com.lenakurasheva.infocratia.mvp.model.entity.auth

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InfocratiaAuthResponse (
        @Expose val accessToken: String,
        @Expose val expiresIn: Int,
        @Expose val tokenType: String,
        @Expose val scope: String,
        @Expose val refreshToken: String
): Parcelable

