package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainPresenter(): MvpPresenter<MainView>() {

    @Inject lateinit var router: Router

    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache
    @Inject lateinit var auth: IAuth
    @Inject lateinit var authRepo: IInfocratiaAuthRepo


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.GroupsScreen())

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        isUserAuth()

    };

    fun isUserAuth(){
        if (auth.accountExists()) {
            infocratiaUserCache.getUser()
                .observeOn(uiScheduler)
                .subscribe { user->
                    if (user != null && user.email == auth.getAccountEmail()) {
                        viewState.signIn()
                    } else { viewState.signOut()}
                }
        }
    }


    fun backClick() {
        router.exit()
    }

    fun bottomMenuGroupsClicked(): Boolean {
        print("bottomMenuGroupsClicked")
        router.replaceScreen(Screens.GroupsScreen())
        return true
    }

    fun bottomMenuThemesClicked(): Boolean {
        router.replaceScreen(Screens.ThemesScreen())
        return true
    }

    fun bottomMenuCabinetClicked(): Boolean {
        //TODO искать юзера в бд
        infocratiaUserCache.getUser()
            .observeOn(uiScheduler)
            .subscribe ({ user ->
                if (user != null && user.email == auth.getAccountEmail()) {
                    signOut()
                } else {
                    viewState.openSignInIntent()
                }
            },{error -> error.printStackTrace()})
        return true
    }

    fun signOut(){
        //TODO удалять юзера из бд
        infocratiaUserCache.deleteUser()
            .observeOn(uiScheduler)
            .subscribe {
                auth.signOut()
                viewState.signOut()
                }
            }

    fun getAccessToken(authCode: String?, idToken: String?) {
        if (authCode != null && idToken != null) {
            var accessToken: String? = null

            val googleParams = HashMap<String?, String?>()
            googleParams.put("grant_type", "authorization_code")
            googleParams.put("client_id", auth.getServerClientId())
            googleParams.put("client_secret", auth.getClientSecret())
            googleParams.put("redirect_uri", "")
            googleParams.put("code", authCode)
            googleParams.put("id_token", idToken)
            googleParams.put("access_type", "offline")

            val infocratiaParams = HashMap<String?, String?>()
            infocratiaParams.put("grant_type", "convert_token")
            infocratiaParams.put("client_id", "ojqYNyT3fgnbnfLJYODmM9vTZmyeB1e4rbAckq6N")
            infocratiaParams.put(
                "client_secret",
                "qHUBShaSgGxOnF9z0wqLCwgVctyHwDgjAzQBH03cTYcsbGKsAp3k7wOKO0gTGlEGgoR44tcy7R0wbz9CrZitdhbxqXx99WnA1LEkemsIBsuSKCJ3w6IkKopA1Wlnp1EH"
            )
            infocratiaParams.put("backend", "google-oauth2")

            print(authCode)
            print(idToken)

            authRepo.getGoogleAccessToken(googleParams)
                .observeOn(Schedulers.io())
                .subscribe({ googleResponse ->
                    googleResponse.accessToken.let {
                        infocratiaParams.put("token", it)
                    }
                    authRepo.getInfocratiaAccessToken(infocratiaParams)
                        .observeOn(uiScheduler)
                        .subscribe { infocratiaResponse ->
                            accessToken = infocratiaResponse.accessToken
                            viewState.signIn()
                            //TODO сохранить юзера в бд
                            infocratiaUserCache.putUser(
                                InfocratiaUser(
                                    auth.getAccountId(),
                                    auth.getAccountFamilyName(),
                                    auth.getAccountEmail(),
                                    auth.getAccountPhotoUrl(),
                                    accessToken
                                )
                            ).subscribe()
                        }
                }, { error -> error.printStackTrace() })
        }
    }




}