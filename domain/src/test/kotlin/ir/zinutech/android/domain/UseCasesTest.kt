package ir.zinutech.android.domain

import io.reactivex.Single
import ir.zinutech.android.domain.common.DomainTestUtils
import ir.zinutech.android.domain.common.TestTransformer
import ir.zinutech.android.domain.usecases.GetManufacturers
import ir.zinutech.android.domain.usecases.GetModels
import org.junit.Test
import org.mockito.Mockito

class UseCasesTest {
  @Test
  fun getCarManufacturers() {
    val carsRepository = Mockito.mock(CarsRepository::class.java)
    Mockito.`when`(carsRepository.getManufacturers()).thenReturn(
        Single.just(DomainTestUtils.generateManufacturersEntityList()))

    val getManufacturers = GetManufacturers(TestTransformer(), carsRepository)
    getManufacturers.get(0).test()
        .assertValue { it.size == 5 }
        .assertComplete()
  }

  @Test
  fun getCarManufacturersNoResultsReturnsEmpty() {
    val carsRepository = Mockito.mock(CarsRepository::class.java)
    Mockito.`when`(carsRepository.getManufacturers()).thenReturn(Single.just(emptyList()))

    val getManufacturers = GetManufacturers(TestTransformer(), carsRepository)
    getManufacturers.get(0).test()
        .assertValue { it.isEmpty() }
        .assertComplete()
  }

  @Test
  fun getCarModelsByManufacturer() {
    val manufacturerEntity = DomainTestUtils.getTestManufacturerEntity("130")
    val carsRepository = Mockito.mock(CarsRepository::class.java)

    val getModels = GetModels(TestTransformer(), carsRepository)

    Mockito.`when`(carsRepository.getModels(manufacturer = manufacturerEntity.manufacturer,
        pageIndex = 0)).thenReturn(Single.just(DomainTestUtils.generateModelsEntityList()))

    getModels.getByManufacturer(manufacturerEntity.manufacturer, 0).test()
        .assertValue { it.size == 6 }
        .assertComplete()

  }
}