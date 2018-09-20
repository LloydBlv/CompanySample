package ir.zinutech.android.data.mappers

import ir.zinutech.android.data.api.BuildDatesListResult
import ir.zinutech.android.domain.common.Mapper
import ir.zinutech.android.domain.entities.BuildDateEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BuildDateDataEntityMapper @Inject constructor() : Mapper<BuildDatesListResult, List<BuildDateEntity>>() {
  override fun mapFrom(from: BuildDatesListResult): List<BuildDateEntity>{
    return from.buildDatesList.map {
      BuildDateEntity(it.key)
    }
  }
}