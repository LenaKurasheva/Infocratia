package com.lenakurasheva.infocratia.mvp.presenter

import com.lenakurasheva.infocratia.mvp.model.auth.IAuth
import com.lenakurasheva.infocratia.mvp.model.cache.IInfocratiaUserCache
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.repo.IInfocratiaGroupsRepo
import com.lenakurasheva.infocratia.mvp.presenter.list.IGroupsListPresenter
import com.lenakurasheva.infocratia.mvp.view.GroupsView
import com.lenakurasheva.infocratia.mvp.view.list.GroupItemView
import com.lenakurasheva.infocratia.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class GroupsPresenter(): MvpPresenter<GroupsView>() {

    @Inject
    lateinit var router: Router
    @Inject lateinit var groupsRepoRetrofit: IInfocratiaGroupsRepo
    @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var infocratiaUserCache: IInfocratiaUserCache
    @Inject lateinit var auth: IAuth

    class GroupsListPresenter : IGroupsListPresenter {
        override var itemClickListener: ((GroupItemView) -> Unit)? = null

        val groups = mutableListOf<InfocratiaGroup>()

        override fun bindView(view: GroupItemView) {
            val group = groups[view.pos]
            view.setTitle(group.name)
            group.about?.let { view.setDescription(group.about) }
            group.image?.let { view.loadImage(it) }
        }
        override fun getCount() = groups.size
    }

    val groupsListPresenter = GroupsListPresenter()
    var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        // Check is user auth: find user in db + if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        isUserAuth()
        viewState.init()
        loadData()

        groupsListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.GroupScreen(groupsListPresenter.groups[view.pos]))
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

    fun loadData() {
        disposables.add(groupsRepoRetrofit.getAllGroups()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                {
                    groupsListPresenter.groups.clear()
                    groupsListPresenter.groups.addAll(it)
                    viewState.updateGroupsList()
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

}