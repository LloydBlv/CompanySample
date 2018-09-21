package com.company.android.sample.manufacturers.adapter

import android.animation.AnimatorInflater
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.text.Layout
import android.view.View
import com.company.android.sample.R
import com.company.android.sample.commons.BaseLceAdapter
import com.company.android.sample.commons.RecyclerViewClickListener
import com.company.android.sample.commons.isL
import com.company.android.sample.commons.isM
import com.company.android.sample.commons.BaseViewHolder
import com.company.android.sample.widgets.Divided
import ir.zinutech.android.domain.entities.ManufacturerEntity
import kotlinx.android.synthetic.main.item_manufacturer_layout.view.manufacturer_item_name_tv
import kotlinx.android.synthetic.main.item_manufacturer_layout.view.manufacturer_item_parent

class ManufacturerAdapter(private val spanCount: Int,
    onListItemClickListener: RecyclerViewClickListener) : BaseLceAdapter<BaseViewHolder<ManufacturerEntity>, ManufacturerEntity>(
    onListItemClickListener) {

  companion object {
    const val EVEN_ROW_VIEW_TYPE = 11
    const val ODD_ROW_VIEW_TYPE = 12
  }


  override fun getLayout(viewType: Int): Int = R.layout.item_manufacturer_layout


  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) {
      return EVEN_ROW_VIEW_TYPE
    } else {
      ODD_ROW_VIEW_TYPE
    }
  }

  override fun getViewHolder(parent: View,
      viewType: Int): BaseViewHolder<ManufacturerEntity> = ViewHolder(parent).apply {
    if (viewType == EVEN_ROW_VIEW_TYPE) {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.manufacturer_even_bg))
    } else {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.manufacturer_odd_bg))
    }
  }


  class ViewHolder(view: View) : BaseViewHolder<ManufacturerEntity>(view), Divided {
    init {
      if (isL()) {
        itemView.manufacturer_item_parent.apply {
          stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.raise)
          ViewCompat.setElevation(this, resources.getDimension(R.dimen.z_card))
        }
      }

      if (isM()) {
        itemView.manufacturer_item_name_tv.breakStrategy = Layout.BREAK_STRATEGY_SIMPLE
      }

    }

    override fun bind(item: ManufacturerEntity) {
      itemView.manufacturer_item_name_tv.text = item.manufacturer
      ViewCompat.setTransitionName(itemView, item.id)
//      ViewCompat.setTransitionName(itemView.manufacturer_item_name_tv, item.id)
    }
  }
}