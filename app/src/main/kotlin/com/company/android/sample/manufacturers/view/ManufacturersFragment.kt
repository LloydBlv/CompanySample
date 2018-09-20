package com.company.android.sample.manufacturers.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.company.android.sample.R
import com.company.android.sample.commons.BaseFragment
import com.company.android.sample.commons.getItem
import com.company.android.sample.config.AppConfig
import com.company.android.sample.manufacturers.adapter.ManufacturerAdapter
import com.company.android.sample.manufacturers.domain.ManufacturersVMFactory
import com.company.android.sample.manufacturers.presenter.ManufacturersViewModel
import com.company.android.sample.utils.EndlessRecyclerViewScrollListener
import ir.zinutech.android.domain.entities.ManufacturerEntity
import kotlinx.android.synthetic.main.fragment_manufacturers_layout.manufacturers_fragment_recyclerview
import kotlinx.android.synthetic.main.fragment_manufacturers_layout.manufacturers_fragment_swipe_refresh
import timber.log.Timber
import javax.inject.Inject

class ManufacturersFragment : BaseFragment() {

  companion object {
    const val TAG = "ManufacturersFragment"
    fun newInstance() = ManufacturersFragment()
  }

  @Inject
  lateinit var factory: ManufacturersVMFactory

  private lateinit var viewModel: ManufacturersViewModel


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_manufacturers_layout,
      container, false)

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    (activity?.application as AppConfig).createManufacturerComponent().inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = ViewModelProviders.of(this, factory).get(ManufacturersViewModel::class.java)
    if (savedInstanceState == null) {
      viewModel.getManufacturers()
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.viewState.observe(this, Observer { viewState ->
      if (viewState != null) {
        manufacturers_fragment_swipe_refresh.isRefreshing = viewState.isLoading
        viewState.manufacturers?.let {
          (manufacturers_fragment_recyclerview.adapter as ManufacturerAdapter).addAll(it)
        }
      }
    })

    viewModel.errorState.observe(this, Observer { throwable ->
      throwable?.let {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
      }
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    manufacturers_fragment_swipe_refresh.setOnRefreshListener {
      (manufacturers_fragment_recyclerview.adapter as ManufacturerAdapter).clear()
      viewModel.refreshManufacturers()
    }

    manufacturers_fragment_recyclerview.apply {
      setHasFixedSize(true)
      val linearLayoutManager = LinearLayoutManager(context)
      layoutManager = linearLayoutManager
      addOnScrollListener(object: EndlessRecyclerViewScrollListener(linearLayoutManager){
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
          Timber.d("onLoadMore(), page:[%s], totalItemsCount:[%s]", page, totalItemsCount)
          manufacturers_fragment_swipe_refresh.isRefreshing = true
          viewModel.getNextManufacturers(page)
        }
      })
      adapter = ManufacturerAdapter { view, _ ->
        getItem<ManufacturerEntity>(view)?.let {
          Timber.d("clickedItem:[%s]", it)
        }
      }
    }
  }
  override fun onDestroyView() {
    super.onDestroyView()
    (activity?.application as AppConfig).releaseManufacturerComponent()
  }
}