package com.company.android.sample.carmodels.injection

import com.company.android.sample.commons.AsyncTransformer
import dagger.Module
import dagger.Provides
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.usecases.GetModels

@CarModelsScope
@Module
class CarModelsModule {

  @Provides
  fun provideGetCarModelsUseCase(carsRepository: CarsRepository) = GetModels(
      AsyncTransformer(), carsRepository)

  /*@Provides
  fun provideCarModelsVMFactory(useCase: GetModels, manufacturer: String) = CarModelsVMFactory(
      useCase, manufacturer)*/
}