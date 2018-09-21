package com.company.android.sample.di.modules

import dagger.Module
import dagger.Provides
import ir.zinutech.android.data.api.Api
import ir.zinutech.android.data.repositories.CarsRepositoryImpl
import ir.zinutech.android.data.repositories.RemoteCarsDataStore
import ir.zinutech.android.domain.CarsRepository
import javax.inject.Singleton


@Module
class DataModule {
  @Singleton
  @Provides
  fun provideCarsRepository(api: Api): CarsRepository = CarsRepositoryImpl(RemoteCarsDataStore(api))
}