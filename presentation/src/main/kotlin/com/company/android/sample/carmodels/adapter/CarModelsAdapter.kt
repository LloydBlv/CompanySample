package com.company.android.sample.carmodels.adapter

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
import ir.zinutech.android.domain.entities.ModelEntity
import kotlinx.android.synthetic.main.item_model_layout.view.models_item_name_tv
import kotlinx.android.synthetic.main.item_model_layout.view.models_item_parent

class CarModelsAdapter(private val spanCount: Int,
    onListItemClickListener: RecyclerViewClickListener) : BaseLceAdapter<BaseViewHolder<ModelEntity>, ModelEntity>(
    onListItemClickListener) {

  companion object {
    const val EVEN_ROW_VIEW_TYPE = 11
    const val ODD_ROW_VIEW_TYPE = 12
  }


  override fun getLayout(viewType: Int): Int = R.layout.item_model_layout


  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) {
      return EVEN_ROW_VIEW_TYPE
    } else {
      ODD_ROW_VIEW_TYPE
    }
  }

  override fun getViewHolder(parent: View,
      viewType: Int): BaseViewHolder<ModelEntity> = ViewHolder(parent).apply {
    if (viewType == EVEN_ROW_VIEW_TYPE) {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.carmodels_even_bg))
    } else {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.carmodels_odd_bg))
    }
  }


  class ViewHolder(view: View) : BaseViewHolder<ModelEntity>(view), Divided {
    init {
      if (isL()) {
        itemView.models_item_parent.apply {
          stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.raise)
          ViewCompat.setElevation(this, resources.getDimension(R.dimen.z_card))
        }
      }

      if (isM()) {
        itemView.models_item_name_tv.breakStrategy = Layout.BREAK_STRATEGY_SIMPLE
      }

    }

    override fun bind(item: ModelEntity) {
      ViewCompat.setTransitionName(itemView.models_item_name_tv, item.model)
      itemView.models_item_name_tv.text = item.model
    }
  }
}