package com.company.android.sample.commons

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> Single<T>.toMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.networkCall(): Single<T> = subscribeOn(Schedulers.io())