package com.company.android.sample.di.components

import com.company.android.sample.builddates.injection.BuildDatesModule
import com.company.android.sample.builddates.injection.BuildDatesSubComponent
import com.company.android.sample.carmodels.injection.CarModelsModule
import com.company.android.sample.carmodels.injection.CarModelsSubComponent
import com.company.android.sample.di.modules.ContextModule
import com.company.android.sample.di.modules.DataModule
import com.company.android.sample.di.modules.NetModule
import com.company.android.sample.manufacturers.injection.ManufacturersModule
import com.company.android.sample.manufacturers.injection.ManufacturersSubComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
  (ContextModule::class),
  (NetModule::class),
  (DataModule::class)])
interface MainComponent {
  fun plus(manufacturersModule: ManufacturersModule): ManufacturersSubComponent
  fun plus(carModelsModule: CarModelsModule): CarModelsSubComponent
  fun plus(buildDatesModule: BuildDatesModule): BuildDatesSubComponent
}