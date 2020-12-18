package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.view.GroupView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class GroupPresenter(val group: InfocratiaGroup): MvpPresenter<GroupView>()  {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setName(group.name)
        group.about?.let { viewState.setAbout(group.about) }
        viewState.setCreationDate(group.creationDate)
        group.image?.let { viewState.loadImage(group.image) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}