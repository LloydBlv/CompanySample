package ir.zinutech.android.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
  @GET("car-types/manufacturer")
  fun getManufacturers(
      @Query("page") pageIndex: Int): Single<ManufacturersListResult>

  @GET("car-types/main-types")
  fun getModels(
      @Query("manufacturer") manufacturer: String,
      @Query("page") pageIndex: Int): Single<ModelsListResult>


  @GET("car-types/built-dates")
  fun getBuildDates(
      @Query("manufacturer") manufacturer: String,
      @Query("main-type") model: String,
      @Query("page") pageIndex: Int): Single<BuildDatesListResult>
}