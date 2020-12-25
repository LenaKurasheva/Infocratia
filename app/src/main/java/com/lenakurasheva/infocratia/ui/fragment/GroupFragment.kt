package com.lenakurasheva.infocratia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.model.entity.InfocratiaGroup
import com.lenakurasheva.infocratia.mvp.model.image.IImageLoader
import com.lenakurasheva.infocratia.mvp.presenter.GroupPresenter
import com.lenakurasheva.infocratia.mvp.view.GroupView
import com.lenakurasheva.infocratia.ui.App
import com.lenakurasheva.infocratia.ui.BackButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_group.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class GroupFragment: MvpAppCompatFragment(), GroupView, BackButtonListener {

    @Inject lateinit var imageLoader: IImageLoader<ImageView>

    companion object {
        fun newInstance(group: InfocratiaGroup) = GroupFragment().apply {
            arguments = Bundle().apply {
                putParcelable("group", group)
            }
        }
    }

    val presenter: GroupPresenter by moxyPresenter {
        val group = arguments?.getParcelable<InfocratiaGroup>("group") as InfocratiaGroup
        GroupPresenter(group).apply { App.instance.groupsSubcomponent?.inject(this) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_group, null)

    override fun init() {
        App.instance.groupsSubcomponent?.inject(this)
    }
    override fun setName(name: String) { tv_name.text = name }
    override fun setAbout(about: String) { tv_about.text = about }
    override fun setCreationDate(creationDate: String) { tv_creation_date.text = creationDate }
    override fun loadImage(image: String) = imageLoader.loadInto(image, iv_image)

    override fun backPressed() = presenter.backClick()

    override fun setGroupsMenuItemChecked() {
        activity?.bottom_navigation?.menu?.findItem(R.id.groups)?.isChecked = true
    }

    override fun setThemesMenuItemChecked() {
        activity?.bottom_navigation?.menu?.findItem(R.id.themes)?.isChecked = true
    }


}