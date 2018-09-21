package com.company.android.sample.builddates.presenter

import android.arch.lifecycle.MutableLiveData
import com.company.android.sample.builddates.domain.BuildDatesViewState
import com.company.android.sample.commons.BaseViewModel
import com.company.android.sample.commons.SingleLiveEvent
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.usecases.GetBuildDates
import timber.log.Timber

class BuildDateViewModel(private val getBuildDatesUseCase: GetBuildDates,
    private val manufacturer: String,
    private val model: String) : BaseViewModel() {

  var viewState: MutableLiveData<BuildDatesViewState> = MutableLiveData()
  var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

  private var pageIndex: MutableLiveData<Int> = MutableLiveData()

  init {
    viewState.value = BuildDatesViewState()
    pageIndex.value = 0
  }

  fun refreshBuildDates() {
    pageIndex.value = 0
    getBuildDates()
  }

  fun getBuildDates() {
    addDisposable(
        getBuildDatesUseCase.getByModel(manufacturer, model, pageIndex.value!!)
            .subscribe({ buildDates ->
              viewState.value?.let {
                val newState = this.viewState.value?.copy(isLoading = false,
                    pageBuildDates = buildDates)

                val newTotalBuildDates = ArrayList<BuildDateEntity>()
                if (newState?.buildDates?.isNotEmpty() == true) {
                  newTotalBuildDates.addAll(newState.buildDates!!)
                }

                if (newState?.pageBuildDates?.isNotEmpty() == true) {
                  newTotalBuildDates.addAll(newState.pageBuildDates!!)
                }
                newState?.buildDates = newTotalBuildDates
                this.viewState.value = newState
                this.errorState.value = null
              }
            }, {
              Timber.e(it, "while getBuildDatesUseCase()")
              viewState.value = viewState.value?.copy(isLoading = false)
              errorState.value = it
            })
    )
  }
}