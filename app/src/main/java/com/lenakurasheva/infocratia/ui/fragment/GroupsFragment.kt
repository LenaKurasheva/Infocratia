package com.lenakurasheva.infocratia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class GroupsFragment : MvpAppCompatFragment(), GroupsView, BackButtonListener,
    MySubscriptionsButtonListener {

    companion object {
        fun newInstance() = GroupsFragment()
    }

    val presenter by moxyPresenter {
        GroupsPresenter().apply {
            App.instance.appComponent?.inject(this)
        }
    }

    val adapter by lazy {
        GroupsRvAdapter(presenter.groupsListPresenter).apply { App.instance.appComponent?.inject(this)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_groups, null)

    override fun init() {
        rv_groups.layoutManager = LinearLayoutManager(requireContext())
        rv_groups.adapter = adapter
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
    }

    override fun backPressed() = presenter.backClick()
    override fun mySubscriptionsPressed(): Boolean {
        TODO("Not yet implemented")
    }
}