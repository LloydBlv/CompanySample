package com.company.android.sample.commons

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  protected fun addDisposable(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }

  protected fun clearDisposables(){
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    clearDisposables()
  }
}