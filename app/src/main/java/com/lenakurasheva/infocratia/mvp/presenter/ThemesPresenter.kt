package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaTheme
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaThemesRepo
import com.lenakurasheva.infocratia.mvp.presenter.list.IThemesListPresenter
import com.lenakurasheva.infocratia.mvp.view.ThemesView
import com.lenakurasheva.infocratia.mvp.view.list.ThemeItemView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ThemesPresenter : MvpPresenter<ThemesView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var themesRepoRetrofit: IInfocratiaThemesRepo
    @Inject
    lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache
    @Inject lateinit var auth: IAuth

    class ThemesListPresenter : IThemesListPresenter {
        override var itemClickListener: ((ThemeItemView) -> Unit)? = null

        val themes = mutableListOf<InfocratiaTheme>()

        override fun bindView(view: ThemeItemView) {
            val theme = themes[view.pos]
            view.setTitle(theme.name)
            theme.about?.let { view.setDescription(theme.about) }
            view.setPubDate(theme.pubDate)
        }
        override fun getCount() = themes.size
    }

    val themesListPresenter = ThemesListPresenter()
    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        // Check is user auth: find user in db + if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        isUserAuth()
        viewState.init()
        loadAllThemes()

        themesListPresenter.itemClickListener = { view ->
//            router.navigateTo(Screens.ThemeScreen(themesListPresenter.themes[view.pos]))
        }
    }

    fun isUserAuth(){
        if (auth.accountExists()) {
            infocratiaUserCache.getUser()
                .observeOn(uiScheduler)
                .subscribe { user ->
                    if (user != null && user.email == auth.getAccountEmail()) {
                        viewState.signIn()
                    } else {
                        viewState.signOut()
                    }
                }
        } else viewState.signOut()
    }

    fun loadAllThemes() {
        disposables.add(themesRepoRetrofit.getAllThemes()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    themesListPresenter.themes.clear()
                    themesListPresenter.themes.addAll(it)
                    viewState.updateThemesList()
                    viewState.allThemesPressed()
                    if(auth.accountExists()) viewState.myThemesIsNotPressed()
                },
                { println("onError: ${it.message}") }))
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun myThemesButtonPassed() {
        disposables.add(themesRepoRetrofit.getUserThemes()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {themesListPresenter.themes.clear()
                themesListPresenter.themes.addAll(it)
                viewState.updateThemesList()
                viewState.myThemesPressed()
                viewState.allThemesIsNotPressed()

                },
                { println("onError: ${it.message}") }))

    }

}