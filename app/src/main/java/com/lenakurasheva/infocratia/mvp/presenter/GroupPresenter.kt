package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.di.groups.IGroupsScopeContainer
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.view.GroupView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class GroupPresenter(val group: InfocratiaGroup): MvpPresenter<GroupView>()  {

    @Inject lateinit var router: Router
    @Inject lateinit var backStack: Stack<String>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setName(group.name)
        group.about?.let { viewState.setAbout(group.about) }
        viewState.setCreationDate(group.creationDate)
        group.image?.let { viewState.loadImage(group.image) }
    }

    fun backClick(): Boolean {
        if(backStack.size > 1) {
            backStack.pop()
            val currFragmentName: String = backStack.peek()
            if (currFragmentName == "GroupsFragment") viewState.setGroupsMenuItemChecked()
            if (currFragmentName == "ThemesFragment") viewState.setThemesMenuItemChecked()
            if (currFragmentName == "GroupFragment") viewState.setGroupsMenuItemChecked()
        }
        router.exit()
        return true
    }
}