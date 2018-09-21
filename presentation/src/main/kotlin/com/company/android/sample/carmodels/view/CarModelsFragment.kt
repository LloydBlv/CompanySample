package com.company.android.sample.carmodels.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewCompat
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
import com.company.android.sample.commons.isL
import com.company.android.sample.commons.toGone
import com.company.android.sample.commons.toVisible
import com.company.android.sample.config.AppConfig
import com.company.android.sample.utils.EndlessRecyclerViewScrollListener
import com.company.android.sample.widgets.GridItemDividerDecoration
import ir.zinutech.android.domain.entities.ModelEntity
import ir.zinutech.android.domain.usecases.GetModels
import kotlinx.android.synthetic.main.fragment_carmodels_layout.carmodels_fragment_header_tv
import kotlinx.android.synthetic.main.fragment_carmodels_layout.carmodels_fragment_pb
import kotlinx.android.synthetic.main.fragment_carmodels_layout.carmodels_fragment_recyclerview
import kotlinx.android.synthetic.main.item_model_layout.view.models_item_name_tv
import timber.log.Timber
import javax.inject.Inject

class CarModelsFragment : BaseFragment() {

  companion object {
    const val TAG = "CarModelsFragment"
    fun newInstance(manufacturerId: String, manufacturer: String) = CarModelsFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_MANUFACTURER_ID, manufacturerId)
        putString(ARG_MANUFACTURER, manufacturer)
      }
    }

    private const val ARG_MANUFACTURER_ID = "arg_manufacturer_id"
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

    postponeEnterTransition()
    if (isL()) {
      sharedElementEnterTransition = android.transition.TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    viewModel = ViewModelProviders.of(this,
        CarModelsVMFactory(getModelsUseCase, arguments?.getString(
            ARG_MANUFACTURER_ID) ?: throw IllegalArgumentException())).get(
        CarModelViewModel::class.java)
    if (savedInstanceState == null) {
      viewModel.getModels()
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.viewState.observe(this, Observer { viewState ->
      if (viewState != null) {
        if (viewState.isLoading) {
          carmodels_fragment_pb.toVisible()
        } else {
          carmodels_fragment_pb.toGone()
        }
        (carmodels_fragment_recyclerview.adapter as CarModelsAdapter).apply {
          if (itemCount == 0) {
            if (viewState.carModels?.isNotEmpty() == true) {
              addAll(viewState.carModels!!)
            }
          } else {
            viewState.pageCarModels?.let {
              (carmodels_fragment_recyclerview.adapter as CarModelsAdapter).addAll(it)
              viewState.pageCarModels = null
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

    if (isL()) {
      ViewCompat.setTransitionName(carmodels_fragment_header_tv, arguments?.getString(
          ARG_MANUFACTURER_ID))

      carmodels_fragment_header_tv.text = arguments?.getString(ARG_MANUFACTURER)
      startPostponedEnterTransition()
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
          viewModel.getNextModels()
        }
      })
      adapter = CarModelsAdapter(spanCount) { view, _ ->
        getItem<ModelEntity>(view)?.let {

          val modelNameTv = view.findViewById<View>(R.id.models_item_name_tv)

          fragmentManager
              ?.beginTransaction()
              ?.addSharedElement(modelNameTv, ViewCompat.getTransitionName(modelNameTv))
              ?.replace(R.id.container, BuildDatesFragment.newInstance(arguments?.getString(
                  ARG_MANUFACTURER) ?: throw java.lang.IllegalArgumentException(),
                  arguments?.getString(
                  ARG_MANUFACTURER_ID) ?: throw java.lang.IllegalArgumentException(),
                  it.model),
                  BuildDatesFragment.TAG)
              ?.addToBackStack(null)
//              ?.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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