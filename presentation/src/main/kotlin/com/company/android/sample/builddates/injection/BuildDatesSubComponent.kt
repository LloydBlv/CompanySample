package com.company.android.sample.builddates.injection

import com.company.android.sample.builddates.view.BuildDatesFragment
import dagger.Subcomponent


@Subcomponent(modules = [BuildDatesModule::class])
interface BuildDatesSubComponent {
  fun inject(buildDatesFragment: BuildDatesFragment)
}