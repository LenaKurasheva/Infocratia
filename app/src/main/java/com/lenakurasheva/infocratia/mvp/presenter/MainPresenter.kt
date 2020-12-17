package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter(): MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.GroupsScreen())
    }

    fun backClick() {
        router.exit()
    }

    fun bottomMenuGroupsClicked(): Boolean {
        router.replaceScreen(Screens.GroupsScreen())
        return true
    }

    fun bottomMenuThemesClicked(): Boolean {
    return true //TODO
    }

    fun bottomMenuCabinetClicked(): Boolean {
    return true //TODO
    }

}