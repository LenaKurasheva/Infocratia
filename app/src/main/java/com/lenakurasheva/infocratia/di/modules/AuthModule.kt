package com.lenakurasheva.infocratia.di.modules

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.api.IGoogleAuth
import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.cache.room.RoomInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.room.db.Database
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import com.lenakurasheva.infocratia.mvp.model.repo.retrofit.RetrofitInfocratiaAuthRepo
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.auth.AndroidAuth
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import javax.inject.Named
import javax.inject.Singleton

@Module
class AuthModule {

    @Named("requestCodeSignIn")
    @Singleton
    @Provides
    fun requestCode(): Int = 5555

    @Named("serverClientId")
    @Singleton
    @Provides
    fun serverClientId() =
        "123473994034-9r6dr52ce9a012n07d4i7at5mk8k1uq2.apps.googleusercontent.com" // application type:: web application

    @Named("clientSecret")
    @Singleton
    @Provides
    fun clientSecret() = "TbKnzuLs5pwnA3R5pJuQSytw"

    @Named("authToken")
    @Singleton
    @Provides
    fun authToken(infocratiaUserCache: IInfocratiaUserCache): String =
    try {"Bearer " +
        infocratiaUserCache.getUser()
            .observeOn(Schedulers.io())
            .blockingGet()?.accessToken.toString()
    } catch (e: RuntimeException){
        e.printStackTrace()
        ""
    }


    @Provides
    fun  googleSignInOptions(@Named("serverClientId") serverClientId: String):  GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(serverClientId)
        .requestServerAuthCode(serverClientId, false)
        .requestEmail()
        .requestProfile()
        .build()

    @Provides
    fun googleSignInClient(app: App, googleSignInOptions: GoogleSignInOptions): GoogleSignInClient = GoogleSignIn.getClient(app, googleSignInOptions)

    @Provides
    fun account(app: App): GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(app)

    @Singleton
    @Provides
    fun authRepo(api: IDataSource, googleAuthApi: IGoogleAuth): IInfocratiaAuthRepo = RetrofitInfocratiaAuthRepo(api, googleAuthApi)

    @Named("googleAuthUrl")
    @Provides
    fun baseUrl() = "https://www.googleapis.com/"

    @Singleton
    @Provides
    fun googleAuthApi (@Named("googleAuthUrl") baseUrl: String, gson: Gson, client: OkHttpClient): IGoogleAuth = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IGoogleAuth::class.java)

    @Singleton
    @Provides
    fun infocratiaUserCache(db: Database): IInfocratiaUserCache = RoomInfocratiaUserCache(db)

    @Singleton
    @Provides
    fun auth(@Named("serverClientId") serverClientId: String,
             @Named("clientSecret") clientSecret: String,
             @Named("requestCodeSignIn") requestCodeSignIn: Int,
             account: GoogleSignInAccount?,
             googleSignInClient: GoogleSignInClient): IAuth = AndroidAuth(serverClientId, clientSecret, requestCodeSignIn, account, googleSignInClient)
}