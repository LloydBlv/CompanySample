package com.company.android.sample

import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar.BaseCallback
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.company.android.sample.manufacturers.view.ManufacturersFragment
import kotlinx.android.synthetic.main.activity_main.container

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


  var isBackPressedOnce = false
  override fun onBackPressed() {

    if (isBackPressedOnce || supportFragmentManager.backStackEntryCount > 0) {
      super.onBackPressed()
    } else {
      Snackbar.make(container, R.string.double_press_to_exit, Snackbar.LENGTH_SHORT)
          .setAction(R.string.exit){
            finish()
          }
          .addCallback(object: BaseCallback<Snackbar>(){
            override fun onShown(transientBottomBar: Snackbar?) {
              super.onShown(transientBottomBar)
              isBackPressedOnce = true
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
              super.onDismissed(transientBottomBar, event)
              isBackPressedOnce = false
            }
          })
          .show()
    }
  }
}
