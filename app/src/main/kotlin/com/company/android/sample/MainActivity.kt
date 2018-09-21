package com.company.android.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.company.android.sample.manufacturers.view.ManufacturersFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager
          .beginTransaction()
          .replace(R.id.container, ManufacturersFragment.newInstance(), ManufacturersFragment.TAG)
          .commitNow()
    }
  }
}
