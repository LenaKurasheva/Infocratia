package com.lenakurasheva.infocratia.mvp.model.auth

interface IAuth {
    fun googleSignInClient(): Any
    fun  requestCodeSignIn(): Int
    fun getGoogleSignInAccount(data: Any?)

    fun accountExists(): Boolean

    fun getAccountEmail(): String
    fun getAccountFamilyName(): String
    fun getAccountPhotoUrl(): String
    fun getAccountId(): String

    fun signOut()

    fun getServerClientId(): String
    fun getClientSecret(): String
    fun getBackendClientSecret(): String
    fun getBackendClientId(): String

    fun getAuthCode(): String?
    fun getIdToken(): String?
}