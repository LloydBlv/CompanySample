package ir.zinutech.android.data.repositories

import io.reactivex.Single
import ir.zinutech.android.domain.CarsRepository
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.entities.ModelEntity

class CarsRepositoryImpl(private val remoteCarsDataStore: RemoteCarsDataStore) : CarsRepository {

  override fun getManufacturers(
      pageIndex: Int): Single<List<ManufacturerEntity>> = remoteCarsDataStore.getManufacturers(
      pageIndex)

  override fun getModels(manufacturer: String,
      pageIndex: Int): Single<List<ModelEntity>> = remoteCarsDataStore.getModelsByManufacturer(
      manufacturer, pageIndex)

  override fun getBuildDates(manufacturer: String, model: String,
      pageIndex: Int): Single<List<BuildDateEntity>> = remoteCarsDataStore.getBuildDatesByModel(
      manufacturer, model, pageIndex)
}