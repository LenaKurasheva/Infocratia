package com.lenakurasheva.infocratia.mvp.model.repo.retrofit

import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.api.IGoogleAuth
import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.entity.auth.GoogleAuthResponse
import com.lenakurasheva.infocratia.mvp.model.entity.auth.InfocratiaAuthResponse
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitInfocratiaAuthRepo(val api: IDataSource, val googleAuthApi: IGoogleAuth, val auth: IAuth): IInfocratiaAuthRepo {

    override fun getGoogleAccessToken(params: HashMap<String?, String?>): Single<GoogleAuthResponse> =
        googleAuthApi.getAccessToken(params).subscribeOn(Schedulers.io())


    override fun getInfocratiaAccessToken(params: HashMap<String?, String?>): Single<InfocratiaAuthResponse> =
        api.postToken(params).subscribeOn(Schedulers.io())

    override fun getAccessToken(authCode: String?, idToken: String?): Single<InfocratiaAuthResponse>  {
        val googleParams = HashMap<String?, String?>()
        googleParams["grant_type"] = "authorization_code"
        googleParams["client_id"] = auth.getServerClientId()
        googleParams["client_secret"] = auth.getClientSecret()
        googleParams["redirect_uri"] = ""
        googleParams["code"] = authCode
        googleParams["id_token"] = idToken
        googleParams["access_type"] = "offline"

        val infocratiaParams = HashMap<String?, String?>()
        infocratiaParams["grant_type"] = "convert_token"
        infocratiaParams["client_id"] = auth.getBackendClientId()
        infocratiaParams["client_secret"] = auth.getBackendClientSecret()
        infocratiaParams["backend"] = "google-oauth2"

        return getGoogleAccessToken(googleParams).flatMap{ it.accessToken.let {
            infocratiaParams.put("token", it)
            getInfocratiaAccessToken(infocratiaParams)}
        }
    }

}