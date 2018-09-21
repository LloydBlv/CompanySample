package com.company.android.sample.builddates.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.company.android.sample.R
import com.company.android.sample.builddates.adapter.BuildDateAdapter
import com.company.android.sample.builddates.domain.BuildDatesVMFactory
import com.company.android.sample.builddates.presenter.BuildDateViewModel
import com.company.android.sample.commons.BaseFragment
import com.company.android.sample.commons.getItem
import com.company.android.sample.commons.isL
import com.company.android.sample.commons.toGone
import com.company.android.sample.commons.toVisible
import com.company.android.sample.config.AppConfig
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.usecases.GetBuildDates
import kotlinx.android.synthetic.main.fragment_builddates_layout.builddates_fragment_header_tv
import kotlinx.android.synthetic.main.fragment_builddates_layout.builddates_fragment_pb
import kotlinx.android.synthetic.main.fragment_builddates_layout.builddates_fragment_recyclerview
import javax.inject.Inject

class BuildDatesFragment : BaseFragment() {

  companion object {
    const val TAG = "BuildDatesFragment"
    fun newInstance(manufacturer: String, manufacturerId: String,
        model: String) = BuildDatesFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_MANUFACTURER_ID, manufacturerId)
        putString(ARG_MANUFACTURER, manufacturer)
        putString(ARG_CAR_MODEL, model)
      }
    }

    private const val ARG_MANUFACTURER_ID = "arg_manufacturer_id"
    private const val ARG_MANUFACTURER = "arg_manufacturer"
    private const val ARG_CAR_MODEL = "arg_car_model"
  }

  @Inject
  lateinit var getBuildDatesUseCase: GetBuildDates

  private lateinit var viewModel: BuildDateViewModel


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_builddates_layout,
      container, false)

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    (activity?.application as AppConfig).createBuildDatesComponent().inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    postponeEnterTransition()
    if (isL()) {
      sharedElementEnterTransition = android.transition.TransitionInflater.from(
          context).inflateTransition(android.R.transition.move)
    }

    viewModel = ViewModelProviders.of(this,
        BuildDatesVMFactory(getBuildDatesUseCase, arguments?.getString(
            ARG_MANUFACTURER_ID) ?: throw IllegalArgumentException(),
            arguments?.getString(ARG_CAR_MODEL) ?: throw IllegalArgumentException())).get(
        BuildDateViewModel::class.java)
    if (savedInstanceState == null) {
      viewModel.getBuildDates()
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.viewState.observe(this, Observer { viewState ->
      if (viewState != null) {
        if (viewState.isLoading) {
          builddates_fragment_pb.toVisible()
        } else {
          builddates_fragment_pb.toGone()
        }
        (builddates_fragment_recyclerview.adapter as BuildDateAdapter).apply {
          if (itemCount == 0) {
            if (viewState.buildDates?.isNotEmpty() == true) {
              addAll(viewState.buildDates!!)
            }
          } else {
            viewState.pageBuildDates?.let {
              (builddates_fragment_recyclerview.adapter as BuildDateAdapter).addAll(it)
              viewState.pageBuildDates = null
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
      ViewCompat.setTransitionName(builddates_fragment_header_tv, arguments?.getString(
          ARG_CAR_MODEL))

      builddates_fragment_header_tv.text = getString(R.string.car_manufacturer_model_token,
          arguments?.getString(ARG_MANUFACTURER), arguments?.getString(ARG_CAR_MODEL))
      startPostponedEnterTransition()
    }
    builddates_fragment_recyclerview.apply {
      setHasFixedSize(true)
      val linearLayoutManager = LinearLayoutManager(context)
      layoutManager = linearLayoutManager
      adapter = BuildDateAdapter { view, _ ->
        getItem<BuildDateEntity>(view)?.let {}
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    (activity?.application as AppConfig).releaseCarModelsComponent()
  }
}