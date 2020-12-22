package com.lenakurasheva.infocratia.mvp.model.entity.auth

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

    @Parcelize
    data class GoogleAuthResponse (
        @Expose val expiresIn: Int,
        @Expose val tokenType: String,
        @Expose val refreshToken: String,
        @Expose val idToken: String,
        @Expose val accessToken: String
        ): Parcelable