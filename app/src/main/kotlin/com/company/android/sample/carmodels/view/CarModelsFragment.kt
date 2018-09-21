package com.company.android.sample.carmodels.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.company.android.sample.R
import com.company.android.sample.builddates.view.BuildDatesFragment
import com.company.android.sample.carmodels.adapter.CarModelsAdapter
import com.company.android.sample.carmodels.domain.CarModelsVMFactory
import com.company.android.sample.carmodels.presenter.CarModelViewModel
import com.company.android.sample.commons.BaseFragment
import com.company.android.sample.commons.getItem
import com.company.android.sample.config.AppConfig
import com.company.android.sample.utils.EndlessRecyclerViewScrollListener
import com.company.android.sample.widgets.GridItemDividerDecoration
import ir.zinutech.android.domain.entities.ModelEntity
import ir.zinutech.android.domain.usecases.GetModels
import kotlinx.android.synthetic.main.fragment_carmodels_layout.carmodels_fragment_recyclerview
import kotlinx.android.synthetic.main.fragment_carmodels_layout.carmodels_fragment_swipe_refresh
import timber.log.Timber
import javax.inject.Inject

class CarModelsFragment : BaseFragment() {

  companion object {
    const val TAG = "CarModelsFragment"
    fun newInstance(manufacturer: String) = CarModelsFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_MANUFACTURER, manufacturer)
      }
    }

    private const val ARG_MANUFACTURER = "arg_manufacturer"
  }


  @Inject
  lateinit var getModelsUseCase: GetModels

  private lateinit var viewModel: CarModelViewModel


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_carmodels_layout,
      container, false)

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    (activity?.application as AppConfig).createCarModelsComponent().inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = ViewModelProviders.of(this,
        CarModelsVMFactory(getModelsUseCase, arguments?.getString(
            ARG_MANUFACTURER) ?: throw IllegalArgumentException())).get(
        CarModelViewModel::class.java)
    if (savedInstanceState == null) {
      viewModel.getModels()
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.viewState.observe(this, Observer { viewState ->
      if (viewState != null) {
        carmodels_fragment_swipe_refresh.isRefreshing = viewState.isLoading
        (carmodels_fragment_recyclerview.adapter as CarModelsAdapter).apply {
          if (itemCount == 0) {
            if (viewState.carModels?.isNotEmpty() == true) {
              addAll(viewState.carModels!!)
            }
          } else {
            viewState.pageCarModels?.let {
              (carmodels_fragment_recyclerview.adapter as CarModelsAdapter).addAll(it)
            }
          }
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
    carmodels_fragment_swipe_refresh.setOnRefreshListener {
      (carmodels_fragment_recyclerview.adapter as CarModelsAdapter).clear()
      viewModel.refreshModels()
    }

    carmodels_fragment_recyclerview.apply {
      setHasFixedSize(true)
      val spanCount = resources.getInteger(R.integer.num_columns)
      val linearLayoutManager = GridLayoutManager(context, spanCount)
      addItemDecoration(GridItemDividerDecoration(context, R.dimen.divider_height,
          R.color.divider))
      layoutManager = linearLayoutManager
//      itemAnimator = SlideInUpAnimator()
      addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
          Timber.d("onLoadMore(), page:[%s], totalItemsCount:[%s]", page, totalItemsCount)
          carmodels_fragment_swipe_refresh.isRefreshing = true
          viewModel.getNextModels()
        }
      })
      adapter = CarModelsAdapter(spanCount) { view, _ ->
        getItem<ModelEntity>(view)?.let {
          fragmentManager
              ?.beginTransaction()
              ?.add(R.id.container, BuildDatesFragment.newInstance(arguments?.getString(
                  ARG_MANUFACTURER) ?: throw java.lang.IllegalArgumentException(), it.model),
                  BuildDatesFragment.TAG)
              ?.addToBackStack(null)
              ?.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
              ?.commit()
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    (activity?.application as AppConfig).releaseCarModelsComponent()
  }
}