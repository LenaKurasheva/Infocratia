package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.api.IGoogleAuth
import com.lenakurasheva.infocratia.mvp.model.entity.auth.GoogleAuthResponse
import com.lenakurasheva.infocratia.mvp.model.entity.auth.InfocratiaAuthResponse
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitInfocratiaAuthRepo(val api: IDataSource, val googleAuthApi: IGoogleAuth): IInfocratiaAuthRepo {

    override fun getGoogleAccessToken(params: HashMap<String?, String?>): Single<GoogleAuthResponse> =
        googleAuthApi.getAccessToken(params).subscribeOn(Schedulers.io())


    override fun getInfocratiaAccessToken(params: HashMap<String?, String?>): Single<InfocratiaAuthResponse> =
        api.postToken(params).subscribeOn(Schedulers.io())

}