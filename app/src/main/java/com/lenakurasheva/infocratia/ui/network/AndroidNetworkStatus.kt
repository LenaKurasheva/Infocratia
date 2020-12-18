package com.lenakurasheva.infocratia.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.lenakurasheva.infocratia.mvp.network.INetworkStatus
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AndroidNetworkStatus(context: Context) : INetworkStatus {

    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                statusSubject.onNext(true)
                print("***NETWORK AVAILABLE*** ")
            }

            override fun onLost(network: Network) {
                statusSubject.onNext(false)
                print("***NETWORK IS NOT AVAILABLE*** ")
            }
        })
    }

    override fun isOnline() = statusSubject
    override fun inOnlineSingle() = statusSubject.first(false)

}