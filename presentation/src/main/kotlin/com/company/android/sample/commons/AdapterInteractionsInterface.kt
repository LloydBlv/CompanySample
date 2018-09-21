package com.company.android.sample.commons

import java.util.ArrayList

/**
 * Created by mohammadrezanajafi on 9/30/16.
 */
interface AdapterInteractionsInterface<O> {
  val mItems: ArrayList<O>

  fun notifyRangeInsert(lastIndex: Int, count: Int)
  fun internalNotifyItemInserted(position: Int)
  fun notifyRangeRemove(lastIndex: Int, count: Int)
  fun internalNotifyDataSetChanged()
  fun internalNotifyItemRemoved(position: Int)

  fun addAll(newData: List<O>) {
    val lastIndex = mItems.size
    mItems.addAll(newData)
    notifyRangeInsert(lastIndex, newData.size)
  }

  fun add(newData: O) {
    mItems.add(newData)
    notifyRangeInsert(mItems.lastIndex, 1)
  }

  fun replace(newData: List<O>) {
    mItems.clear()
    mItems.addAll(newData)
    internalNotifyDataSetChanged()
  }

  fun addToPosition(newData: O, position: Int) {
    mItems.add(position, newData)
    internalNotifyItemInserted(position)
  }

  fun removePosition(position: Int) {
    val isSuccess = mItems.removeAt(position)
    internalNotifyItemRemoved(position)
  }

  fun addAllSilent(newData: List<O>) {
    mItems.addAll(newData)
    internalNotifyDataSetChanged()
  }

  fun clear() {
    val lastIndex = mItems.size
    mItems.clear()
    notifyRangeRemove(0, lastIndex)
  }

  fun clearNoAnimation() {
    mItems.clear()
    internalNotifyDataSetChanged()
  }

  fun addAllToFront(list: List<O>) {
    mItems.addAll(0, list)
    notifyRangeInsert(0, list.size)

  }

}