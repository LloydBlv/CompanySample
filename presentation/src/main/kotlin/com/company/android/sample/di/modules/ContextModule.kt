package com.company.android.sample.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ContextModule constructor(context: Context){

  private val appContext = context.applicationContext

  @Singleton
  @Provides
  fun provideAppContext(): Context = appContext
}