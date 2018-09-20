package com.company.android.sample.config

import android.app.Application
import com.company.android.sample.BuildConfig
import com.company.android.sample.di.components.DaggerMainComponent
import com.company.android.sample.di.components.MainComponent
import com.company.android.sample.di.modules.ContextModule
import com.company.android.sample.di.modules.DataModule
import com.company.android.sample.di.modules.NetModule
import com.company.android.sample.manufacturers.injection.ManufacturersModule
import com.company.android.sample.manufacturers.injection.ManufacturersSubComponent
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import timber.log.Timber.DebugTree

class AppConfig: Application() {

  lateinit var mainComponent: MainComponent
  private var manufacturersComponent: ManufacturersSubComponent? = null
  override fun onCreate() {
    super.onCreate()

    Timber.plant(DebugTree())

    if(LeakCanary.isInAnalyzerProcess(this)) return

    LeakCanary.install(this)

    mainComponent = DaggerMainComponent.builder()
        .contextModule(ContextModule(this))
        .netModule(NetModule(BuildConfig.API_KEY))
        .dataModule(DataModule())
        .build()

  }

  fun createManufacturerComponent(): ManufacturersSubComponent{
    manufacturersComponent = mainComponent.plus(ManufacturersModule())
    return manufacturersComponent!!
  }

  fun releaseManufacturerComponent() {
    manufacturersComponent = null
  }
}