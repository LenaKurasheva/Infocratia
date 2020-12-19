package com.lenakurasheva.infocratia.navigation

import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.ui.fragment.GroupFragment
import com.lenakurasheva.infocratia.ui.fragment.GroupsFragment
import com.lenakurasheva.infocratia.ui.fragment.ThemesFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class GroupsScreen() : SupportAppScreen() {
        override fun getFragment() = GroupsFragment.newInstance()
    }

    class GroupScreen(val group: InfocratiaGroup) : SupportAppScreen() {
        override fun getFragment() = GroupFragment.newInstance(group)
    }

    class ThemesScreen() : SupportAppScreen() {
        override fun getFragment() = ThemesFragment.newInstance()
    }

}