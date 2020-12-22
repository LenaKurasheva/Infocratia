package com.lenakurasheva.infocratia.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.network.AndroidNetworkStatus
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    val hostUrl = "http://infocratia.space/"

    @Named("baseUrl v1")
    @Provides
    fun baseUrl(): String {
        val baseUrl = hostUrl
        return baseUrl
    }
    //    fun baseUrl() = "http://192.168.1.39/api/v1/"


    @Singleton
    @Provides
    fun api(@Named("baseUrl v1") baseUrl: String, gson: Gson, client: OkHttpClient): IDataSource = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IDataSource::class.java)

    @Singleton
    @Provides
    fun gson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Singleton
    @Provides
    fun client() = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
        .build()

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)
}