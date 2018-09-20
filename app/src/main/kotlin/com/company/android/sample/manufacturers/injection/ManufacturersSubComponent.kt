package com.company.android.sample.manufacturers.injection

import com.company.android.sample.manufacturers.view.ManufacturersFragment
import dagger.Subcomponent


@Subcomponent(modules = [ManufacturersModule::class])
interface ManufacturersSubComponent {
  fun inject(manufacturersFragment: ManufacturersFragment)
}