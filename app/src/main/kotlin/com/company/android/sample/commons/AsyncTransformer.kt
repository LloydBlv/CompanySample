package com.company.android.sample.commons

import io.reactivex.Single
import io.reactivex.SingleSource
import ir.zinutech.android.domain.common.Transformer

class AsyncTransformer<T>: Transformer<T>() {
  override fun apply(upstream: Single<T>): SingleSource<T> {
    return upstream.networkCall().toMainThread()
  }
}