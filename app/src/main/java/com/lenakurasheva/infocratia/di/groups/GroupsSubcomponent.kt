package com.lenakurasheva.infocratia.di.groups

import com.lenakurasheva.infocratia.di.GroupsScope
import com.lenakurasheva.infocratia.di.groups.module.GroupsModule
import com.lenakurasheva.infocratia.mvp.presenter.GroupPresenter
import com.lenakurasheva.infocratia.mvp.presenter.GroupsPresenter
import com.lenakurasheva.infocratia.ui.adapter.GroupsRvAdapter
import com.lenakurasheva.infocratia.ui.fragment.GroupFragment
import dagger.Subcomponent

@GroupsScope
@Subcomponent( modules = [GroupsModule::class])

interface GroupsSubcomponent {
    fun inject(groupsPresenter: GroupsPresenter)
    fun inject(groupPresenter: GroupPresenter)
    fun inject (groupFragment: GroupFragment)
    fun inject(groupsRvAdapter: GroupsRvAdapter)
}