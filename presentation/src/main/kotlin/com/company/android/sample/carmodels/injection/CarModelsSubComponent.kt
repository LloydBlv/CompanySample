package com.company.android.sample.carmodels.injection

import com.company.android.sample.carmodels.view.CarModelsFragment
import dagger.Subcomponent


@Subcomponent(modules = [CarModelsModule::class])
interface CarModelsSubComponent {
  fun inject(carModelsFragment: CarModelsFragment)
}