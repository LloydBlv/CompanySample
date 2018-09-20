package com.company.android.sample.commons

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber


fun View.toVisible(){
  visibility = View.VISIBLE
}

fun View.toGone(){
  visibility = View.GONE
}

typealias RecyclerViewClickListener = (View, Int) -> (Unit)


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, isAttachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, isAttachToRoot)
}


fun RecyclerView.findRowPosition(view: View?): Int? {
  val pos: Int = getChildAdapterPosition(view)
  if (pos >= 0) {
    return pos
  }
  return null
}

fun <T> RecyclerView.getItem(position: Int): T? {
  return (adapter as? AdapterInteractionsInterface<T>)?.mItems?.get(position)
}

fun <T> RecyclerView.getData(): List<T>? {
  return (adapter as? AdapterInteractionsInterface<T>)?.mItems
}

fun <T> RecyclerView.getLceAdapter() = adapter as T

fun <T> RecyclerView.getItem(view: View?): T? {
  val pos = findRowPosition(view)
  Timber.d("getItem(), pos:[%s]", pos)
  return if (pos != null) {
    getItem(pos)
  } else {
    null
  }
}