package com.lenakurasheva.infocratia.mvp.presenter

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named


class MainPresenter(): MvpPresenter<MainView>() {

    @Inject lateinit var router: Router


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.GroupsScreen())

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        if(account != null) {
//            viewState.updateUI(account)
//    }
        };


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

//        if (account == null) {
            viewState.openSignInIntent()
//        } else {
//            viewState.signOut()
//        }
        return true
    }

//    fun signOutCompleted(){
        // viewState.updateUI(null)
//        account = null
//    }


}