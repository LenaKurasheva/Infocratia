package com.lenakurasheva.infocratia.mvp.model.api

import com.lenakurasheva.infocratia.mvp.model.entity.auth.GoogleAuthResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IGoogleAuth {

    @FormUrlEncoded
    @POST(value = "oauth2/v4/token")
    fun getAccessToken(@FieldMap params: Map<String?, String?>): Single<GoogleAuthResponse>
}