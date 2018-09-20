package ir.zinutech.android.data.mappers

import ir.zinutech.android.data.entities.ManufacturerData
import ir.zinutech.android.domain.common.Mapper
import ir.zinutech.android.domain.entities.ManufacturerEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ManufacturerDataEntityMapper @Inject constructor() : Mapper<ManufacturerData, List<ManufacturerEntity>>() {
  override fun mapFrom(from: ManufacturerData): List<ManufacturerEntity>{
    return from.manufacturersList.map {
      ManufacturerEntity(it.key, it.value)
    }
  }
}