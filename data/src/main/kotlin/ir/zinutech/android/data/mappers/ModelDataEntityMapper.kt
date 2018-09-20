package ir.zinutech.android.data.mappers

import ir.zinutech.android.data.api.ModelsListResult
import ir.zinutech.android.domain.common.Mapper
import ir.zinutech.android.domain.entities.ModelEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ModelDataEntityMapper @Inject constructor() : Mapper<ModelsListResult, List<ModelEntity>>() {
  override fun mapFrom(from: ModelsListResult): List<ModelEntity>{
    return from.modelsList.map {
      ModelEntity(it.key)
    }
  }
}