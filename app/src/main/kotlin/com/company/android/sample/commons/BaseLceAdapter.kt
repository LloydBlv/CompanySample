package com.company.android.sample.commons

import android.support.annotation.LayoutRes
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.company.android.sample.R
import com.company.android.sample.models.entities.BaseViewHolder
import java.util.ArrayList
import android.os.Build.VERSION_CODES.O
import android.support.v7.widget.RecyclerView
import android.os.Build.VERSION_CODES.O
import timber.log.Timber


/**
 * Created by mohammadrezanajafi on 8/30/16.
 */
abstract class BaseLceAdapter<VH : BaseViewHolder<O>, O>(
    private var onItemClickListener: RecyclerViewClickListener? = null,
    initialData: List<O>? = null) : BaseRecyclerViewAdapter<VH, O>(), AdapterInteractionsInterface<O> {
  private val mItemsN: ArrayList<O> = ArrayList()


  companion object {
    const val LOADING_VIEW_TYPE = 1999
  }

  init {
    initialData?.let {
      mItemsN.addAll(it)
    }
  }

  override val mItems: ArrayList<O>
    get() = mItemsN

  @LayoutRes
  abstract fun getLayout(viewType: Int): Int

  abstract fun getViewHolder(parent: View, viewType: Int = 0): VH

  open fun configOnClickListeners(viewType: Int, view: View) {}

  override fun getItemCount(): Int = mItems.size

  class LoadingViewHolder(view: View) : BaseViewHolder<Unit>(view) {
    override fun bind(item: Unit) {}
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.bind(mItems[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val viewHolder = getViewHolder(parent.inflate(getLayout(viewType)), viewType)
    configOnClickListeners(viewType, viewHolder.itemView)
    onItemClickListener?.let { onClickFunc ->
      viewHolder.itemView?.let {
        it.setOnClickListener {
          onClickFunc.invoke(it, viewType)
        }
      }
    }
    return viewHolder
  }

  override fun notifyRangeInsert(lastIndex: Int, count: Int) {
    notifyItemRangeInserted(lastIndex, count)
  }

  override fun notifyRangeRemove(lastIndex: Int, count: Int) {
    notifyItemRangeRemoved(lastIndex, count)
  }

  override fun internalNotifyDataSetChanged() {
    notifyDataSetChanged()
  }

  override fun internalNotifyItemInserted(position: Int) {
    notifyItemInserted(position)
  }

  override fun internalNotifyItemRemoved(position: Int) {
    notifyItemRemoved(position)
  }
}