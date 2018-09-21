package com.company.android.sample.manufacturers.presenter

import android.arch.lifecycle.MutableLiveData
import com.company.android.sample.commons.BaseViewModel
import com.company.android.sample.commons.SingleLiveEvent
import com.company.android.sample.commons.toMainThread
import com.company.android.sample.manufacturers.domain.ManufacturersViewState
import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.usecases.GetManufacturers
import timber.log.Timber
import java.util.Collections.addAll
import java.util.concurrent.TimeUnit.MILLISECONDS

class ManufacturersViewModel(private val getManufacturersUseCase: GetManufacturers): BaseViewModel() {

  var viewState: MutableLiveData<ManufacturersViewState> = MutableLiveData()
  var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

  private var pageIndex: MutableLiveData<Int> = MutableLiveData()

  init {
    viewState.value = ManufacturersViewState()
    pageIndex.value = 0
  }

  fun getNextManufacturers(){
    pageIndex.value = pageIndex.value!! + 1
    getManufacturers()
  }
  fun refreshManufacturers(){
    pageIndex.value = 0
    getManufacturers()
  }
  fun getManufacturers(){
//    if(true) return
    addDisposable(
        getManufacturersUseCase.get(pageIndex.value!!)
            .subscribe({ manufacturers ->
              viewState.value?.let {
                val newState = this.viewState.value?.copy(isLoading = false, pageManufacturers = manufacturers)

                val newTotalManufacturers = ArrayList<ManufacturerEntity>()
                if (newState?.manufacturers?.isNotEmpty() == true) {
                  newTotalManufacturers.addAll(newState.manufacturers!!)
                }

                if (newState?.pageManufacturers?.isNotEmpty() == true) {
                  newTotalManufacturers.addAll(newState.pageManufacturers!!)
                }
                newState?.manufacturers = newTotalManufacturers
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