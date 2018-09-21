package com.company.android.sample.carmodels.presenter

import android.arch.lifecycle.MutableLiveData
import com.company.android.sample.carmodels.domain.CarModelsViewState
import com.company.android.sample.commons.BaseViewModel
import com.company.android.sample.commons.SingleLiveEvent
import ir.zinutech.android.domain.entities.ModelEntity
import ir.zinutech.android.domain.usecases.GetModels
import timber.log.Timber

class CarModelViewModel(private val getModelsUseCase: GetModels,
    private val manufacturer: String) : BaseViewModel() {

  var viewState: MutableLiveData<CarModelsViewState> = MutableLiveData()
  var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

  private var pageIndex: MutableLiveData<Int> = MutableLiveData()

  init {
    viewState.value = CarModelsViewState()
    pageIndex.value = 0
  }

  fun getNextModels() {
    pageIndex.value = pageIndex.value!! + 1
    getModels()
  }

  fun refreshModels() {
    pageIndex.value = 0
    getModels()
  }

  fun getModels() {
//    if(true) return
    addDisposable(
        getModelsUseCase.getByManufacturer(manufacturer, pageIndex.value!!)
            .subscribe({ carModels ->
              viewState.value?.let {
                val newState = this.viewState.value?.copy(isLoading = false,
                    pageCarModels = carModels)

                val newTotalCarModels = ArrayList<ModelEntity>()
                if (newState?.carModels?.isNotEmpty() == true) {
                  newTotalCarModels.addAll(newState.carModels!!)
                }

                if (newState?.pageCarModels?.isNotEmpty() == true) {
                  newTotalCarModels.addAll(newState.pageCarModels!!)
                }
                newState?.carModels = newTotalCarModels
                this.viewState.value = newState
                this.errorState.value = null
              }
            }, {
              Timber.e(it, "while getManufacturersUseCase()")
              viewState.value = viewState.value?.copy(isLoading = false)
              errorState.value = it
            })
    )
  }
}