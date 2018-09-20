package ir.zinutech.android.domain.usecases

import io.reactivex.Single
import ir.zinutech.android.domain.common.Transformer

abstract class UseCase<T>(private val transformer: Transformer<T>) {

  abstract fun createSingle(data: Map<String, Any>? = null): Single<T>

  fun single(withData: Map<String, Any>? = null): Single<T> {
    return createSingle(withData).compose(transformer)
  }

}