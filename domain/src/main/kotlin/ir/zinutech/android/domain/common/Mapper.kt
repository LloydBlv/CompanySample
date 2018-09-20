package ir.zinutech.android.domain.common

import io.reactivex.Single
import ir.zinutech.android.domain.entities.Optional

abstract class Mapper<in E, T> {
  abstract fun mapFrom(from: E): T

  fun mapOptional(from: Optional<E>): Optional<T> {
    from.value?.let {
      return Optional.of(mapFrom(it))
    } ?: return Optional.empty()
  }

  fun single(from: E): Single<T> {
    return Single.fromCallable { mapFrom(from) }
  }

  fun single(from: List<E>): Single<List<T>> {
    return Single.fromCallable { from.map { mapFrom(it) } }
  }
}