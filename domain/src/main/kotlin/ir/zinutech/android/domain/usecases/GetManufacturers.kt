package ir.zinutech.android.domain.usecases

import io.reactivex.Single
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.common.Transformer
import ir.zinutech.android.domain.entities.ManufacturerEntity

class GetManufacturers(transformer: Transformer<List<ManufacturerEntity>>,
    private val carsRepository: CarsRepository) : UseCase<List<ManufacturerEntity>>(transformer) {

  companion object {
    private const val PARAM_PAGE_INDEX = "param:page_index"
  }

  fun get(pageIndex: Int): Single<List<ManufacturerEntity>> = single(
      hashMapOf(PARAM_PAGE_INDEX to pageIndex))

  override fun createSingle(data: Map<String, Any>?): Single<List<ManufacturerEntity>> = data?.get(
      PARAM_PAGE_INDEX)?.let { pageIndex ->
    carsRepository.getManufacturers(pageIndex as Int)
  } ?: Single.just(emptyList())
}