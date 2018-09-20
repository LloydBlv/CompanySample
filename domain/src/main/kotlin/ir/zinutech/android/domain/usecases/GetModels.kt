package ir.zinutech.android.domain.usecases

import io.reactivex.Single
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.common.Transformer
import ir.zinutech.android.domain.entities.ModelEntity

class GetModels(transformer: Transformer<List<ModelEntity>>,
    private val carsRepository: CarsRepository) : UseCase<List<ModelEntity>>(transformer) {

  companion object {
    private const val PARAM_PAGE_INDEX = "param:page_index"
    private const val PARAM_MANUFACTURER = "param:manufacturer"
  }

  fun getByManufacturer(manufacturer: String, pageIndex: Int): Single<List<ModelEntity>> = single(
      hashMapOf(PARAM_MANUFACTURER to manufacturer,
          PARAM_PAGE_INDEX to pageIndex))

  override fun createSingle(data: Map<String, Any>?): Single<List<ModelEntity>> = data?.get(
      PARAM_PAGE_INDEX)?.let { pageIndex ->
    data.get(PARAM_MANUFACTURER)?.let { manufacturer ->
      carsRepository.getModels(manufacturer as String, pageIndex as Int)
    }
  } ?: Single.just(emptyList())
}