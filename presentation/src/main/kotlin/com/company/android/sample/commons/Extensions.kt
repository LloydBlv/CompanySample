package com.company.android.sample.commons

import android.os.Build
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun isM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun isMorLower(): Boolean = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M
fun isN(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
fun isL(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun isO(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O


fun <T> Single<T>.toMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.networkCall(): Single<T> = subscribeOn(Schedulers.io())