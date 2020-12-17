package com.lenakurasheva.infocratia.navigation

import com.lenakurasheva.infocratia.ui.fragment.GroupsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class GroupsScreen() : SupportAppScreen() {
        override fun getFragment() = GroupsFragment.newInstance()
    }
}