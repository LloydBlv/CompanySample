package com.company.android.sample.carmodels.domain

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.company.android.sample.carmodels.presenter.CarModelViewModel
import ir.zinutech.android.domain.usecases.GetModels

class CarModelsVMFactory(
    private val useCase: GetModels,
    private val manufacturer: String
): ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return CarModelViewModel(useCase, manufacturer) as T
  }

}