package com.lenakurasheva.infocratia.mvp.model.api

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.entity.auth.InfocratiaAuthResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface IDataSource {

    @GET(value = "api/v1/groups")
    fun getAllGroups(): Single<List<InfocratiaGroup>>

    @GET(value = "api/v1/themes")
    fun getAllThemes(): Single<List<InfocratiaTheme>>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET(value = "api/v1/my-themes")
    fun getUserThemes(@Header("AUTHORIZATION") accessToken: String): Single<List<InfocratiaTheme>>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET(value = "api/v1/my-groups")
    fun getUserGroups(@Header("AUTHORIZATION") accessToken: String): Single<List<InfocratiaGroup>>

    @FormUrlEncoded
    @POST(value = "auth/convert-token")
    fun postToken(@FieldMap params: Map<String?, String?>): Single<InfocratiaAuthResponse>


}