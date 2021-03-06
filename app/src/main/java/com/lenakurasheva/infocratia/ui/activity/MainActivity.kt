package com.lenakurasheva.infocratia.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.presenter.MainPresenter
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import com.lenakurasheva.infocratia.ui.MySubscriptionsButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var androidAuth: IAuth


    val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container){
        override fun applyCommands(commands: Array<out Command>) {
            presenter.checkCurrentBottomMenuItem(getCurrentScreenName(commands))
            super.applyCommands(commands)
        }
    }

    fun getCurrentScreenName(commands: Array<out Command>): String {
        var currentScreenName = ""
        when (val lastCommand: Command = commands[commands.size - 1]){
                is Replace -> currentScreenName = lastCommand.screen.screenKey
                is Forward -> currentScreenName = lastCommand.screen.screenKey
                is Back -> currentScreenName = getPreviousFragmentName() ?: ""
            }
        return currentScreenName
    }

    private fun getPreviousFragmentName(): String? {
        return when (supportFragmentManager.backStackEntryCount){
            0 -> null
            1 -> presenter.primaryScreen.toString()
            else -> supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
        }
    }


    val presenter by moxyPresenter {
        App.instance.appComponent.inject(this)

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

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == androidAuth.requestCodeSignIn()) {
            androidAuth.getGoogleSignInAccount(data)
            presenter.getAccessToken(androidAuth.getAuthCode(), androidAuth.getIdToken())
        }
    }

    override fun openSignInIntent() {
        val signInIntent: Intent =  (androidAuth.googleSignInClient() as GoogleSignInClient).signInIntent
        this.startActivityForResult(signInIntent, androidAuth.requestCodeSignIn())
    }

    override fun signOut() {
        bottom_navigation.menu.findItem(R.id.cabinet).setTitle(getString(R.string.enter))
        supportFragmentManager.fragments.forEach {
            if (it is MySubscriptionsButtonListener) {
                findViewById<TextView>(R.id.subscriptions_tv).isEnabled = false
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    findViewById<TextView>(R.id.subscriptions_tv).setTextColor(resources.getColor(R.color.common_google_signin_btn_text_dark_disabled, null))
                }
            }
        }
    }

    override fun signIn(){
        bottom_navigation.menu.findItem(R.id.cabinet).title = getString(R.string.exit)
        supportFragmentManager.fragments.forEach {
            if (it is MySubscriptionsButtonListener) {
                findViewById<TextView>(R.id.subscriptions_tv).isEnabled = true
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    findViewById<TextView>(R.id.subscriptions_tv).setTextColor(resources.getColor(android.R.color.tab_indicator_text, null))
                }
            }
        }
    }

    override fun setGroupsMenuItemChecked() {
        bottom_navigation.menu.findItem(R.id.groups).isChecked = true
    }

    override fun setThemesMenuItemChecked() {
        bottom_navigation.menu.findItem(R.id.themes).isChecked = true
    }

}