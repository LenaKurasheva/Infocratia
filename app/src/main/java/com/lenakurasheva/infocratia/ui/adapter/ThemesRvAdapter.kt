package com.lenakurasheva.infocratia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenakurasheva.infocratia.R
import com.lenakurasheva.infocratia.mvp.presenter.list.IThemesListPresenter
import com.lenakurasheva.infocratia.mvp.view.list.ThemeItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_group.view.tv_description
import kotlinx.android.synthetic.main.item_group.view.tv_title
import kotlinx.android.synthetic.main.item_theme.view.*

class ThemesRvAdapter (val presenter: IThemesListPresenter) : RecyclerView.Adapter<ThemesRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false)).apply {
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
        ThemeItemView,
        LayoutContainer {
        override var pos = -1

        override fun setTitle(text: String) = with(containerView){
            tv_title.text = text
        }

        override fun setDescription(text: String) = with(containerView){
            tv_description.text = text

        }

        override fun setPubDate(text: String) = with(containerView){
            tv_pub_date.text = text
        }

    }

}