package com.company.android.sample.builddates.domain

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.company.android.sample.builddates.presenter.BuildDateViewModel
import com.company.android.sample.manufacturers.presenter.ManufacturersViewModel
import ir.zinutech.android.domain.usecases.GetBuildDates
import ir.zinutech.android.domain.usecases.GetManufacturers

class BuildDatesVMFactory(
    private val useCase: GetBuildDates,
    private val manufacturer: String,
    private val model: String
): ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return BuildDateViewModel(useCase, manufacturer, model) as T
  }

}