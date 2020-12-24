package com.lenakurasheva.infocratia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.presenter.ThemesPresenter
import com.lenakurasheva.infocratia.mvp.view.ThemesView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import com.lenakurasheva.infocratia.ui.MySubscriptionsButtonListener
import com.lenakurasheva.infocratia.ui.adapter.ThemesRvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_themes.*
import kotlinx.android.synthetic.main.fragment_themes.subscriptions_tv
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ThemesFragment : MvpAppCompatFragment(), ThemesView, BackButtonListener,
    MySubscriptionsButtonListener {

    companion object {
        fun newInstance() = ThemesFragment()
    }

    val presenter by moxyPresenter {
        ThemesPresenter().apply {
            App.instance.appComponent?.inject(this)
        }
    }

    val adapter by lazy {
        ThemesRvAdapter(presenter.themesListPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_themes, null)

    override fun init() {
        rv_themes.layoutManager = LinearLayoutManager(requireContext())
        rv_themes.adapter = adapter
        subscriptions_tv.setOnClickListener{ mySubscriptionsPressed()}
        all_tv.setOnClickListener { presenter.loadAllThemes() }
    }

    override fun updateThemesList() {
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

    override fun mySubscriptionsPressed() {
        presenter.myThemesButtonPassed()
    }
}