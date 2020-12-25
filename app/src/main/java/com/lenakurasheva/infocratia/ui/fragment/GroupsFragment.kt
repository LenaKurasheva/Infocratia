package com.lenakurasheva.infocratia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.presenter.GroupsPresenter
import com.lenakurasheva.infocratia.mvp.view.GroupsView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import com.lenakurasheva.infocratia.ui.MySubscriptionsButtonListener
import com.lenakurasheva.infocratia.ui.adapter.GroupsRvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.android.synthetic.main.fragment_groups.all_tv
import kotlinx.android.synthetic.main.fragment_groups.subscriptions_tv
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class GroupsFragment : MvpAppCompatFragment(), GroupsView, BackButtonListener,
    MySubscriptionsButtonListener {

    companion object {
        fun newInstance() = GroupsFragment()
    }

    val presenter by moxyPresenter {
        App.instance.initGroupsComponent()
        GroupsPresenter().apply {
            App.instance.groupsSubcomponent?.inject(this)
        }
    }

    val adapter by lazy {
        GroupsRvAdapter(presenter.groupsListPresenter).apply { App.instance.groupsSubcomponent?.inject(this)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_groups, null)

    override fun init() {
        rv_groups.layoutManager = LinearLayoutManager(requireContext())
        rv_groups.adapter = adapter
        subscriptions_tv.setOnClickListener{ mySubscriptionsPressed()}
        all_tv.setOnClickListener { presenter.loadAllGroups() }
    }

    override fun updateGroupsList() {
        adapter.notifyDataSetChanged()
    }

    override fun signIn() {
        activity?.bottom_navigation?.menu?.findItem(R.id.cabinet)?.title = "Выйти"
        subscriptions_tv.isEnabled = true
    }

    override fun signOut() {
        activity?.bottom_navigation?.menu?.findItem(R.id.cabinet)?.title = "Войти"
        subscriptions_tv.isEnabled = false
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subscriptions_tv.setTextColor(resources.getColor(R.color.common_google_signin_btn_text_dark_disabled, null))
        }

    }

    override fun allGroupsPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            all_tv.setTextColor(resources.getColor(R.color.purple_500, null))
        }
    }

    override fun myGroupsPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subscriptions_tv.setTextColor(resources.getColor(R.color.purple_500, null))
        }
    }

    override fun myGroupsIsNotPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subscriptions_tv.setTextColor(resources.getColor(android.R.color.tab_indicator_text, null))
        }
    }

    override fun allGroupsIsNotPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            all_tv.setTextColor(resources.getColor(android.R.color.tab_indicator_text, null))
        }
    }

    override fun setGroupsMenuItemChecked() {
        activity?.bottom_navigation?.menu?.findItem(R.id.groups)?.isChecked = true
    }

    override fun setThemesMenuItemChecked() {
        activity?.bottom_navigation?.menu?.findItem(R.id.themes)?.isChecked = true
    }

    override fun backPressed() = presenter.backClick()

    override fun mySubscriptionsPressed(){
        presenter.myGroupsButtonPressed()    }
}