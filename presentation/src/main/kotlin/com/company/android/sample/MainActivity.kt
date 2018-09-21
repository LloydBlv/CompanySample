package com.company.android.sample

import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar.BaseCallback
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager.OnBackStackChangedListener
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.company.android.sample.manufacturers.view.ManufacturersFragment
import kotlinx.android.synthetic.main.activity_main.container

class MainActivity : AppCompatActivity() {

  private var onBackStackChangeListener: OnBackStackChangedListener? = null

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

    onBackStackChangeListener = OnBackStackChangedListener {
      updateActionbar()
    }

    updateActionbar()
    supportFragmentManager.addOnBackStackChangedListener(onBackStackChangeListener)
  }

  private fun updateActionbar() {
    val backStackEntryCount = supportFragmentManager.backStackEntryCount
    supportActionBar?.setDisplayHomeAsUpEnabled(backStackEntryCount > 0)
    title = when (backStackEntryCount) {
      0 -> {
        getString(R.string.manufacturers)
      }

      1 -> {
        getString(R.string.models)
      }

      2 -> {
        getString(R.string.build_dates)
      }

      else -> ""
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
        true
      }

      else -> {
        super.onOptionsItemSelected(item)
      }
    }

  }

  override fun onDestroy() {
    super.onDestroy()
    supportFragmentManager.addOnBackStackChangedListener(onBackStackChangeListener)
  }

  var isBackPressedOnce = false
  override fun onBackPressed() {

    if (isBackPressedOnce || supportFragmentManager.backStackEntryCount > 0) {
      super.onBackPressed()
    } else {
      Snackbar.make(container, R.string.double_press_to_exit, Snackbar.LENGTH_SHORT)
          .setAction(R.string.exit) {
            finish()
          }
          .addCallback(object : BaseCallback<Snackbar>() {
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
