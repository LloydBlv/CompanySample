package ir.zinutech.android.domain.usecases

import io.reactivex.Single
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.common.Transformer
import ir.zinutech.android.domain.entities.BuildDateEntity

class GetBuildDates(transformer: Transformer<List<BuildDateEntity>>,
    private val carsRepository: CarsRepository) : UseCase<List<BuildDateEntity>>(transformer) {

  companion object {
    private const val PARAM_PAGE_INDEX = "param:page_index"
    private const val PARAM_MANUFACTURER = "param:manufacturer"
    private const val PARAM_MODEL = "param:model"
  }

  fun getByModel(manufacturer: String, model: String,
      pageIndex: Int): Single<List<BuildDateEntity>> = single(
      hashMapOf(
          PARAM_MANUFACTURER to manufacturer,
          PARAM_MODEL to model,
          PARAM_PAGE_INDEX to pageIndex))

  override fun createSingle(data: Map<String, Any>?): Single<List<BuildDateEntity>> = data?.get(
      PARAM_PAGE_INDEX)?.let { pageIndex ->
    data[PARAM_MANUFACTURER]?.let { manufacturer ->
      data[PARAM_MODEL].let { model ->
        carsRepository.getBuildDates(manufacturer as String, model as String, pageIndex as Int)
      }
    }
  } ?: Single.just(emptyList())
}