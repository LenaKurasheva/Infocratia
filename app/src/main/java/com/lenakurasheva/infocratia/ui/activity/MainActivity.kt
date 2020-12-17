package com.lenakurasheva.infocratia.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.presenter.MainPresenter
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.groups -> presenter.bottomMenuGroupsClicked()
                R.id.themes -> presenter.bottomMenuThemesClicked()
                R.id.cabinet -> presenter.bottomMenuCabinetClicked()
                else -> false
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClick()
    }


}