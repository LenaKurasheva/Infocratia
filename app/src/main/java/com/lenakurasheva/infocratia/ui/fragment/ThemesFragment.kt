package com.lenakurasheva.infocratia.ui.fragment

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.presenter.ThemesPresenter
import com.lenakurasheva.infocratia.mvp.view.ThemesView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import com.lenakurasheva.infocratia.ui.MySubscriptionsButtonListener
import com.lenakurasheva.infocratia.ui.adapter.ThemesRvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.android.synthetic.main.fragment_themes.*
import kotlinx.android.synthetic.main.fragment_themes.all_tv
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
        subscriptions_tv.setTextColor(resources.getColor(R.color.common_google_signin_btn_text_dark_disabled))
    }

    override fun allThemesPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            all_tv.setTextColor(resources.getColor(R.color.purple_500, null))
        }
    }

    override fun myThemesPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subscriptions_tv.setTextColor(resources.getColor(R.color.purple_500, null))
        }
    }

    override fun myThemesIsNotPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            subscriptions_tv.setTextColor(resources.getColor(android.R.color.tab_indicator_text, null))
        }
    }

    override fun allThemesIsNotPressed() {
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

    override fun mySubscriptionsPressed() {
        presenter.myThemesButtonPassed()
    }
}