package com.lenakurasheva.infocratia.mvp.model.api

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET


interface IDataSource {

    @GET(value = "groups")
    fun getAllGroups(): Single<List<InfocratiaGroup>>

//    @GET
//    fun getAllThemes(): Single<List<InfocratiaTheme>>
//
//    @GET
//    fun getGroupsUserSubscribedTo(): Single<List<InfocratiaGroup>>
//
//    @GET
//    fun getThemesUserSubscribedTo(): Single<List<InfocratiaTheme>>

}