package com.company.android.sample.manufacturers.domain

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.company.android.sample.manufacturers.presenter.ManufacturersViewModel
import ir.zinutech.android.domain.usecases.GetManufacturers

class ManufacturersVMFactory(
    private val useCase: GetManufacturers
): ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ManufacturersViewModel(useCase) as T
  }

}