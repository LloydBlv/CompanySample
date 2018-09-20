package ir.zinutech.android.domain.common

import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.entities.ModelEntity

object DomainTestUtils {
  fun generateManufacturersEntityList(): List<ManufacturerEntity> =
      (0..4).map { getTestManufacturerEntity("$it") }


  fun getTestManufacturerEntity(id: String): ManufacturerEntity =
      ManufacturerEntity(id, "Manufacturer_$id")


  fun generateModelsEntityList(): List<ModelEntity> =
      (0..5).map { getTestModelEntity("Model_$it") }


  fun getTestModelEntity(model: String): ModelEntity =
      ModelEntity(model)
}