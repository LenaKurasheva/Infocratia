package com.lenakurasheva.infocratia.ui.activity

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.model.api.IDataSource
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaUser
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaAuthRepo
import com.lenakurasheva.infocratia.mvp.presenter.MainPresenter
import com.lenakurasheva.infocratia.mvp.view.MainView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named


class MainActivity : MvpAppCompatActivity(), MainView {

//    @set:[Inject Named("requestCodeSignIn")] var requestCodeSignIn: Int = 0
    var requestCodeSignIn: Int = 5555

    @field:[Inject Named("serverClientId")] internal lateinit var serverClientId: String
    @field:[Inject Named("clientSecret")] internal lateinit var clientSecret: String

    @Inject
    @JvmField
    var account: GoogleSignInAccount? = null

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject lateinit var authRepo: IInfocratiaAuthRepo

    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache

    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

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
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        if (requestCode == requestCodeSignIn) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
    {
        try {
            account = completedTask.getResult(ApiException::class.java)
            val authCode = account?.serverAuthCode
            val idToken = account?.idToken

            // Here we can send token to server
           if(authCode != null && idToken != null ) { getAccessToken(authCode, idToken) }


            // Signed in successfully, show authenticated UI.
            if (!TextUtils.isEmpty(authCode)) {

                Toast.makeText(
                    getApplicationContext(), authCode,
                    Toast.LENGTH_SHORT
                ).show();
//                updateUI(account);
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d(
//                "Google SignIn",
//                "signInResult:failed code=" + e.getStatusCode() + "; message: " + e.getMessage()
//            );
            e.printStackTrace();
//            updateUI(null);
        }
    }

    override fun openSignInIntent() {
        //TODO искать юзера в бд
        infocratiaUserCache.getUser()
                .observeOn(uiScheduler)
                .subscribe ({ user ->
                    if (user != null && user.email == account?.email) {
                        signOut()
                    } else {
                        val signInIntent: Intent = googleSignInClient.getSignInIntent()
                        this.startActivityForResult(signInIntent, requestCodeSignIn)
                    }
                },{error -> error.printStackTrace()})
//
//        if(account == null){
//            val signInIntent: Intent = googleSignInClient.getSignInIntent()
//            this.startActivityForResult(signInIntent, requestCodeSignIn)
//        } else{
//            signOut()
//        }
    }

    override fun signOut() {
        //TODO удалять юзера из бд
        infocratiaUserCache.deleteUser()
                .observeOn(uiScheduler)
                .subscribe {
                    googleSignInClient.signOut().addOnCompleteListener(this) { task ->
//            presenter.signOutCompleted()
                        account = null
                        updateUi()
                    }
                }

//        googleSignInClient.signOut().addOnCompleteListener(this) { task ->
////            presenter.signOutCompleted()
//            account = null
        }



    fun getAccessToken(authCode: String, idToken: String) {
        var accessToken: String? = null

        val googleParams = HashMap<String?, String?>()
        googleParams.put("grant_type", "authorization_code")
        googleParams.put("client_id", serverClientId)
        googleParams.put("client_secret", clientSecret)
        googleParams.put("redirect_uri", "")
        googleParams.put("code", authCode)
        googleParams.put("id_token", idToken)
        googleParams.put("access_type", "offline")

        val infocratiaParams = HashMap<String?, String?>()
        infocratiaParams.put("grant_type", "convert_token")
        infocratiaParams.put("client_id", "ojqYNyT3fgnbnfLJYODmM9vTZmyeB1e4rbAckq6N")
        infocratiaParams.put(
                "client_secret",
                "qHUBShaSgGxOnF9z0wqLCwgVctyHwDgjAzQBH03cTYcsbGKsAp3k7wOKO0gTGlEGgoR44tcy7R0wbz9CrZitdhbxqXx99WnA1LEkemsIBsuSKCJ3w6IkKopA1Wlnp1EH"
            )
        infocratiaParams.put("backend", "google-oauth2")

        print(authCode)
        print(idToken)

        authRepo.getGoogleAccessToken(googleParams)
                .observeOn(Schedulers.io())
                .subscribe ({ googleResponse ->
                    googleResponse.accessToken.let {
                        infocratiaParams.put("token", it)
                    }
                    authRepo.getInfocratiaAccessToken(infocratiaParams)
                            .observeOn(uiScheduler)
                            .subscribe { infocratiaResponse ->
                                accessToken = infocratiaResponse.accessToken
                                updateUi()
                                //TODO сохранить юзера в бд
                                infocratiaUserCache.putUser(InfocratiaUser(
                                        account?.id,
                                        account?.familyName,
                                        account?.email,
                                        account?.photoUrl.toString(),
                                        accessToken)).subscribe()
                            }
                }, {error -> error.printStackTrace()})
    }

    fun updateUi(){
        if (account == null) bottom_navigation.menu.findItem(R.id.cabinet).setTitle("Войти")
        else bottom_navigation.menu.findItem(R.id.cabinet).setTitle("Выйти")
    }


}