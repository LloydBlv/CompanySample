package com.company.android.sample.builddates.injection

import com.company.android.sample.commons.AsyncTransformer
import com.company.android.sample.manufacturers.domain.ManufacturersVMFactory
import dagger.Module
import dagger.Provides
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.usecases.GetBuildDates
import ir.zinutech.android.domain.usecases.GetManufacturers

@BuildDatesScope
@Module
class BuildDatesModule {

  @Provides
  fun provideGetBuildDatesUseCase(carsRepository: CarsRepository) = GetBuildDates(
      AsyncTransformer(), carsRepository)

}