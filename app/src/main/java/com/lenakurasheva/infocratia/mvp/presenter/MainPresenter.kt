package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.navigation.Screens
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.fragment.GroupsFragment
import com.lenakurasheva.infocratia.ui.fragment.ThemesFragment
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class MainPresenter(): MvpPresenter<MainView>() {

    @Inject lateinit var app: App

    @Inject lateinit var router: Router
    @Inject lateinit var backStack: Stack<String>

    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache
    @Inject lateinit var auth: IAuth
    @Inject lateinit var authRepo: IInfocratiaAuthRepo



    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.GroupsScreen())
        backStack.addElement(GroupsFragment::class.java.simpleName)
    }

    fun backClick() {
        if(backStack.size > 1) {
            backStack.pop()
            val currFragmentName: String = backStack.peek()
            if (currFragmentName == "GroupsFragment") viewState.setGroupsMenuItemChecked()
            if (currFragmentName == "ThemesFragment") viewState.setThemesMenuItemChecked()
            if (currFragmentName == "GroupFragment") viewState.setGroupsMenuItemChecked()
        }
        router.exit()
    }

    fun bottomMenuGroupsClicked(): Boolean {
        print("bottomMenuGroupsClicked")
        router.navigateTo(Screens.GroupsScreen())
        backStack.addElement(GroupsFragment::class.java.simpleName)
        return true
    }

    fun bottomMenuThemesClicked(): Boolean {
        router.navigateTo(Screens.ThemesScreen())
        backStack.addElement(ThemesFragment::class.java.simpleName)
        return true
    }

    fun bottomMenuCabinetClicked(): Boolean {
        //Find user in db:
        infocratiaUserCache.getUser()
            .observeOn(uiScheduler)
            .subscribe({ user ->
                if (user != null && user.email == auth.getAccountEmail()) {
                    signOut()
                } else {
                    viewState.openSignInIntent()
                }
            }, { error -> error.printStackTrace() })
        return true
    }

    fun signOut(){
        //Delete user from db:
        infocratiaUserCache.deleteUser()
            .observeOn(uiScheduler)
            .subscribe {
                auth.signOut()
                viewState.signOut()
                }
            }

    fun getAccessToken(authCode: String?, idToken: String?) {
        if (authCode != null && idToken != null) {
            var accessToken: String?

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
            infocratiaParams["client_id"] = app.getString(R.string.backend_client_id)
            infocratiaParams["client_secret"] = app.getString(R.string.backend_client_secret)
            infocratiaParams["backend"] = "google-oauth2"

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
                            //Save user to db:
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