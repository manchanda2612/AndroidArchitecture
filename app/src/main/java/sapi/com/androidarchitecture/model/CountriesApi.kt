package sapi.com.androidarchitecture.model

import io.reactivex.Single
import retrofit2.http.GET

public interface CountriesApi {

    @GET("all")
    fun getCountries() : Single<List<Country>>


}