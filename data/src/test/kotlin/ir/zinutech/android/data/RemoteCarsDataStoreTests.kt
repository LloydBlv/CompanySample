package ir.zinutech.android.data

import io.reactivex.Single
import ir.zinutech.android.data.api.Api
import ir.zinutech.android.data.api.BuildDatesListResult
import ir.zinutech.android.data.api.ManufacturersListResult
import ir.zinutech.android.data.api.ModelsListResult
import ir.zinutech.android.data.repositories.RemoteCarsDataStore
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class RemoteCarsDataStoreTests {

  private lateinit var api: Api
  private lateinit var remoteCarsDataStore: RemoteCarsDataStore

  @Before
  fun before() {
    api = Mockito.mock(Api::class.java)
    remoteCarsDataStore = RemoteCarsDataStore(api)
  }

  @Test
  fun testWhenRequestingCarsFromRemoteReturnExpectedResult() {
    val cars = hashMapOf<String, String>().apply {
      (0..5).map {
        put("$it", "Manufacturer_$it")
      }
    }

    val manufacturersListResult = ManufacturersListResult(0, 5, cars)

    Mockito.`when`(api.getManufacturers(pageIndex = 0)).thenReturn(
        Single.just(manufacturersListResult))

    remoteCarsDataStore.getManufacturers(pageIndex = 0).test()
        .assertValue { it.size == 6 && it[0].manufacturer == "Manufacturer_0" }
        .assertComplete()
  }

  @Test
  fun testWhenRequestingModelsFromRemoteReturnExpectedResult(){


    val modelsList = hashMapOf<String, String>().apply {
      (0..3).map {
        put("Model_$it", "Model_$it")
      }
    }

    val modelsListResult = ModelsListResult(0, 4, modelsList)
    Mockito.`when`(api.getModels("Manufacturer_0", 0)).thenReturn(Single.just(modelsListResult))

    remoteCarsDataStore.getModelsByManufacturer("Manufacturer_0", 0).test()
        .assertValue { it.size == 4 && it[0].model == "Model_0" }
        .assertComplete()
  }


  @Test
  fun testWhenRequestingBuildDatesFromRemoteReturnExpectedResult(){

    val modelsList = hashMapOf<String, String>().apply {
      (0..9).map {
        put("${2008+it}", "${2008+it}")
      }
    }

    val buildDatesListResult = BuildDatesListResult(modelsList)
    Mockito.`when`(api.getBuildDates("Manufacturer_0", "Model_0", 0)).thenReturn(Single.just(buildDatesListResult))

    remoteCarsDataStore.getBuildDatesByModel("Manufacturer_0", "Model_0", 0).test()
        .assertValue { it.size == 10}
        .assertComplete()
  }
}