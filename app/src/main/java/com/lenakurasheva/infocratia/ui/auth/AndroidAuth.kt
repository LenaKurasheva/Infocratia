package com.lenakurasheva.infocratia.ui.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.lenakurasheva.infocratia.mvp.model.auth.IAuth

class AndroidAuth(val serverClId: String, val clSecret: String,
                  val backendClId: String, val backendClSecret: String,
                  val requestCodeSignIn: Int, var account: GoogleSignInAccount?,
                  var googleSignInClient: GoogleSignInClient,
) : IAuth{

    override fun getGoogleSignInAccount(data: Any?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data as Intent?);

        try {
            account = task.getResult(ApiException::class.java)
        } catch (e: ApiException) {
            e.printStackTrace();
        }
    }

    override fun getAuthCode(): String? {
        val authCode = account?.serverAuthCode
        return authCode
    }

    override fun getIdToken(): String? {
        val idToken = account?.idToken
        return idToken
    }

    override fun googleSignInClient(): Any = googleSignInClient

    override fun requestCodeSignIn(): Int = requestCodeSignIn

    override fun accountExists(): Boolean {
        return (account != null)
    }

    override fun getAccountEmail(): String = account?.email.toString()
    override fun getAccountFamilyName(): String = account?.familyName.toString()
    override fun getAccountPhotoUrl(): String = account?.photoUrl.toString()
    override fun getAccountId(): String = account?.id.toString()

    override fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener { account = null }
        }

    override fun getServerClientId(): String = serverClId
    override fun getClientSecret(): String = clSecret
    override fun getBackendClientSecret(): String = backendClSecret
    override fun getBackendClientId(): String = backendClId

}