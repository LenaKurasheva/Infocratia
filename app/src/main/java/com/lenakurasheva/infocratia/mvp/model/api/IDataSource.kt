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

    @FormUrlEncoded
    @POST(value = "auth/convert-token")
    fun postToken(@FieldMap params: Map<String?, String?>): Single<InfocratiaAuthResponse>


//
//    "grant_type=convert_token&client_id=ojqYNyT3fgnbnfLJYODmM9vTZmyeB1e4rbAckq6N
//    &client_secret=qHUBShaSgGxOnF9z0wqLCwgVctyHwDgjAzQBH03cTYcsbGKsAp3k7wOKO0gTGlEGgoR44tcy7R0wbz9CrZitdhbxqXx99WnA1LEkemsIBsuSKCJ3w6IkKopA1Wlnp1EH
//    &backend=google-oauth2&token=<google_token> http://infocratia.space/auth/convert-token?
//
//    @GET
//    fun getGroupsUserSubscribedTo(): Single<List<InfocratiaGroup>>
//
//    @GET
//    fun getThemesUserSubscribedTo(): Single<List<InfocratiaTheme>>

}