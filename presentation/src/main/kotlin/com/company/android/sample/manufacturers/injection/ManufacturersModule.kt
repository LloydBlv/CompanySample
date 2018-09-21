package com.company.android.sample.manufacturers.injection

import com.company.android.sample.commons.AsyncTransformer
import com.company.android.sample.manufacturers.domain.ManufacturersVMFactory
import dagger.Module
import dagger.Provides
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.usecases.GetManufacturers

@ManufacturersScope
@Module
class ManufacturersModule {

  @Provides
  fun provideGetManufacturersUseCase(carsRepository: CarsRepository) = GetManufacturers(
      AsyncTransformer(), carsRepository)

  @Provides
  fun provideManufacturersVMFactory(useCase: GetManufacturers) = ManufacturersVMFactory(useCase)
}