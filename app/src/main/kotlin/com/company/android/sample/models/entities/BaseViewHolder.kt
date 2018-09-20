package com.company.android.sample.models.entities

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View

/**
 * Created by mohammadrezanajafi on 10/12/17.
 */
abstract class BaseViewHolder<in T>(view: View) : ViewHolder(view) {
  abstract fun bind(item: T)
}
