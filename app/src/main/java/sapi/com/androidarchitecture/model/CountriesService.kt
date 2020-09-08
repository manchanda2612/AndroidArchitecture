package sapi.com.androidarchitecture.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    companion object {
        const val BASE_URL = "https://restcountries.eu/rest/v2/"
    }

   private val api : CountriesApi = getServices()

   fun getServices() : CountriesApi {

       val retrofit = Retrofit.Builder()
       val build = retrofit.baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .build()

       return build.create(CountriesApi::class.java)

   }


    public fun getCountries() : Single<List<Country>> {
        return api.getCountries()
    }


}