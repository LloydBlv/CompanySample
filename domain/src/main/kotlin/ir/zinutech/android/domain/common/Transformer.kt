package ir.zinutech.android.domain.common

import io.reactivex.SingleTransformer

abstract class Transformer<T> : SingleTransformer<T, T>