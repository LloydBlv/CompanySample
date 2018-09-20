package com.company.android.sample.di.modules

import dagger.Module
import dagger.Provides
import ir.zinutech.android.data.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetModule(private val apiKey: String) {

  companion object {
    private const val API_KEY_QUERY = "wa_key"
    private const val PAGE_SIZE_QUERY = "pageSize"
    private const val BASE_URL = "http://api-aws-eu-qa-1.auto1-test.com/v1/"
  }

  @Singleton
  @Provides
  fun provideInterceptors(): ArrayList<Interceptor> {
    val interceptors = arrayListOf<Interceptor>()

    val apiKeyInterceptor = Interceptor { chain ->
      val originalRequest: Request = chain.request()
      val originalHttpUrl = originalRequest.url()

      val url = originalHttpUrl.newBuilder()
          .addQueryParameter(PAGE_SIZE_QUERY, "15")
          .addQueryParameter(API_KEY_QUERY, apiKey)
          .build()

      val requestBuilder = originalRequest.newBuilder()
          .url(url)

      val newRequest = requestBuilder.build()
      return@Interceptor chain.proceed(newRequest)
    }

    interceptors.add(apiKeyInterceptor)
    interceptors.add(HttpLoggingInterceptor().setLevel(BODY))
    return interceptors
  }

  @Singleton
  @Provides
  fun provideRetrofit(interceptors: ArrayList<Interceptor>): Retrofit{
    val okHttpBuilder = OkHttpClient.Builder()
    if (interceptors.isNotEmpty()) {
      interceptors.forEach {
        okHttpBuilder.addInterceptor(it)
      }
    }

    return Retrofit.Builder()
        .client(okHttpBuilder.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
  }

  @Singleton
  @Provides
  fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}