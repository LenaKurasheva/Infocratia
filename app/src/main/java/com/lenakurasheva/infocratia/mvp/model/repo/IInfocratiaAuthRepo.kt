package com.lenakurasheva.infocratia.mvp.model.repo

import com.lenakurasheva.infocratia.mvp.model.entity.auth.GoogleAuthResponse
import com.lenakurasheva.infocratia.mvp.model.entity.auth.InfocratiaAuthResponse
import io.reactivex.rxjava3.core.Single

interface IInfocratiaAuthRepo {
    fun getGoogleAccessToken(params: HashMap<String?, String?>): Single<GoogleAuthResponse>

    fun getInfocratiaAccessToken(params: HashMap<String?, String?>): Single<InfocratiaAuthResponse>

    fun getAccessToken(authCode: String?, idToken: String?): Single<InfocratiaAuthResponse>
}