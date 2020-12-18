package com.lenakurasheva.infocratia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.model.image.IImageLoader
import com.lenakurasheva.infocratia.mvp.presenter.list.IGroupsListPresenter
import com.lenakurasheva.infocratia.mvp.view.list.GroupItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_group.view.*
import javax.inject.Inject

class GroupsRvAdapter (val presenter: IGroupsListPresenter) : RecyclerView.Adapter<GroupsRvAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)).apply {
            containerView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        GroupItemView,
        LayoutContainer {
        val container = containerView.findViewById<ImageView>(R.id.iv_image)
        override var pos = -1

        override fun setTitle(text: String) = with(containerView){
            tv_title.text = text
        }

        override fun setDescription(text: String) = with(containerView){
            tv_description.text = text

        }

        override fun loadImage(url: String) {
            imageLoader.loadInto(url, container)
        }
    }

}