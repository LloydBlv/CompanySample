package ir.zinutech.android.domain

import io.reactivex.Single
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.entities.ModelEntity

interface CarsRepository {
  fun getManufacturers(pageIndex: Int = 0): Single<List<ManufacturerEntity>>
  fun getModels(manufacturer: String, pageIndex: Int): Single<List<ModelEntity>>
  fun getBuildDates(manufacturer: String, model: String, pageIndex: Int): Single<List<BuildDateEntity>>
}