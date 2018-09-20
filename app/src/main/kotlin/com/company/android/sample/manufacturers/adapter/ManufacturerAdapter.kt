package com.company.android.sample.manufacturers.adapter

import android.view.View
import com.company.android.sample.R
import com.company.android.sample.commons.BaseLceAdapter
import com.company.android.sample.commons.RecyclerViewClickListener
import com.company.android.sample.models.entities.BaseViewHolder
import ir.zinutech.android.domain.entities.ManufacturerEntity
import kotlinx.android.synthetic.main.item_manufacturer_layout.view.manufacturer_item_name_tv

class ManufacturerAdapter(
    onListItemClickListener: RecyclerViewClickListener) : BaseLceAdapter<BaseViewHolder<ManufacturerEntity>, ManufacturerEntity>(
    onListItemClickListener) {

  companion object {
    const val EVEN_ROW_VIEW_TYPE = 11
    const val ODD_ROW_VIEW_TYPE = 12
//    const val LOADING_VIEW_TYPE = 13
  }


  override fun getLayout(viewType: Int): Int = when(viewType){
    EVEN_ROW_VIEW_TYPE -> R.layout.item_manufacturer_layout
    else -> R.layout.item_odd_manufacturer_layout
//    ODD_ROW_VIEW_TYPE -> R.layout.item_odd_manufacturer_layout
//    else -> R.layout.infinite_loading
  }


  override fun getItemViewType(position: Int): Int{
    /*return if (isLoading && position == itemCount - 1) {
      LOADING_VIEW_TYPE
    }else */

    return if (position % 2 == 0) {
      EVEN_ROW_VIEW_TYPE
    } else {
      ODD_ROW_VIEW_TYPE
    }
  }

  override fun getViewHolder(parent: View,
      viewType: Int): BaseViewHolder<ManufacturerEntity> = when(viewType){
    EVEN_ROW_VIEW_TYPE -> ViewHolder(parent)
    else -> OddViewHolder(parent)
//    ODD_ROW_VIEW_TYPE -> OddViewHolder(parent)
//    else ->
  }

  class ViewHolder(view: View) : BaseViewHolder<ManufacturerEntity>(view) {
    override fun bind(item: ManufacturerEntity) {
      itemView.manufacturer_item_name_tv.text = item.manufacturer
    }
  }

  class OddViewHolder(view: View) : BaseViewHolder<ManufacturerEntity>(view) {
    override fun bind(item: ManufacturerEntity) {
      itemView.manufacturer_item_name_tv.text = item.manufacturer
    }
  }
}