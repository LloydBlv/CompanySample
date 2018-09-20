package com.company.android.sample.commons

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder

/**
 * Created by mohammadrezanajafi on 9/30/16.
 */
abstract class BaseRecyclerViewAdapter<VH : ViewHolder, O> : Adapter<VH>(),
    AdapterInteractionsInterface<O> {
  open fun updateItemViewParams(width: Int, count: Int) {}
}