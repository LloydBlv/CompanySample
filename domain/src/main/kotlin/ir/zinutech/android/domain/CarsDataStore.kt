package ir.zinutech.android.domain

import io.reactivex.Single
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.entities.ModelEntity

interface CarsDataStore {
  fun getManufacturers(pageIndex: Int): Single<List<ManufacturerEntity>>
  fun getModelsByManufacturer(manufacturer: String, pageIndex: Int): Single<List<ModelEntity>>
  fun getBuildDatesByModel(manufacturer: String, model: String, pageIndex: Int): Single<List<BuildDateEntity>>
}