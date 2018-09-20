package ir.zinutech.android.domain.common

import io.reactivex.Single
import io.reactivex.SingleSource

class TestTransformer<T>: Transformer<T>() {
  override fun apply(upstream: Single<T>): SingleSource<T> {
    return upstream
  }

}