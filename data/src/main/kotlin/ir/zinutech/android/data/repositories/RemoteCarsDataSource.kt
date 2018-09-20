package ir.zinutech.android.data.repositories

import io.reactivex.Single
import ir.zinutech.android.data.api.Api
import ir.zinutech.android.data.mappers.BuildDateDataEntityMapper
import ir.zinutech.android.data.mappers.ManufacturerDataEntityMapper
import ir.zinutech.android.data.mappers.ModelDataEntityMapper
import ir.zinutech.android.domain.CarsDataStore
import ir.zinutech.android.domain.entities.BuildDateEntity
import ir.zinutech.android.domain.entities.ManufacturerEntity
import ir.zinutech.android.domain.entities.ModelEntity

class RemoteCarsDataSource(private val api: Api): CarsDataStore {

  private val manufacturerDataEntityMapper = ManufacturerDataEntityMapper()
  private val modelDataEntityMapper = ModelDataEntityMapper()
  private val buildDateDataEntityMapper = BuildDateDataEntityMapper()

  override fun getManufacturers(pageIndex: Int): Single<List<ManufacturerEntity>> {
    return api.getManufacturers(pageIndex).map {
      manufacturerDataEntityMapper.mapFrom(it)
    }
  }

  override fun getModelsByManufacturer(manufacturer: String,
      pageIndex: Int): Single<List<ModelEntity>> {
    return api.getModels(manufacturer, pageIndex).map {
      modelDataEntityMapper.mapFrom(it)
    }


  }

  override fun getBuildDatesByModel(manufacturer: String, model: String,
      pageIndex: Int): Single<List<BuildDateEntity>> {
    return api.getBuildDates(manufacturer, model, pageIndex).map {
      buildDateDataEntityMapper.mapFrom(it)
    }

  }
}