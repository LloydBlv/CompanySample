package ir.zinutech.android.data.mappers

import ir.zinutech.android.data.api.ManufacturersListResult
import ir.zinutech.android.domain.common.Mapper
import ir.zinutech.android.domain.entities.ManufacturerEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ManufacturerDataEntityMapper @Inject constructor() : Mapper<ManufacturersListResult, List<ManufacturerEntity>>() {
  override fun mapFrom(from: ManufacturersListResult): List<ManufacturerEntity>{
    return from.manufacturersList.map {
      ManufacturerEntity(it.key, it.value)
    }
  }
}