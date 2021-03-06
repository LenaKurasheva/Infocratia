package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.navigation.Screens
import com.lenakurasheva.infocratia.ui.App
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainPresenter: MvpPresenter<MainView>() {

    @Inject lateinit var app: App

    @Inject lateinit var router: Router

    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache
    @Inject lateinit var auth: IAuth
    @Inject lateinit var authRepo: IInfocratiaAuthRepo

    val primaryScreen = Screens.GroupsScreen()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(primaryScreen)
    }

    fun backClick() {
        router.exit()
    }

    fun bottomMenuGroupsClicked(): Boolean {
        print("bottomMenuGroupsClicked")
        router.navigateTo(Screens.GroupsScreen())
        return true
    }

    fun bottomMenuThemesClicked(): Boolean {
        router.navigateTo(Screens.ThemesScreen())
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
            authRepo.getAccessToken(authCode, idToken)
                    .observeOn(uiScheduler)
                    .subscribe ({ response ->
                        accessToken = response.accessToken
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
                    }, { error -> error.printStackTrace() })
        }
    }

    fun checkCurrentBottomMenuItem(currentScreenName: String) {
        println("CURRENT SCREEN: $currentScreenName")
        if (currentScreenName.contains("GroupsScreen")) viewState.setGroupsMenuItemChecked()
        if (currentScreenName.contains("ThemesScreen")) viewState.setThemesMenuItemChecked()
    }

}