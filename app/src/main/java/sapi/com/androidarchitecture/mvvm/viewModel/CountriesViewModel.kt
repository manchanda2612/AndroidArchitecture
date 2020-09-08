package sapi.com.androidarchitecture.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import sapi.com.androidarchitecture.model.CountriesService
import sapi.com.androidarchitecture.model.Country

class CountriesViewModel : ViewModel() {

    private val service = CountriesService()
    private val TAG  = CountriesViewModel::class.java.simpleName
    private val countries : MutableLiveData<List<String>> = MutableLiveData()
    private val countryError : MutableLiveData<Boolean> = MutableLiveData()



    fun getCountries() : LiveData<List<String>> {
        fetchCountries()
        return countries
    }

    fun getError() : LiveData<Boolean> {
        return countryError
    }


    fun fetchCountries() {
        /**
         * This is going to return single observable of RxJava.
         * To resolve this we call subscribeOn() that basically tells the system that is need
         * to be run on background thread
         */
        service.getCountries()
            .subscribeOn(Schedulers.newThread()) // this basically tell that all the computation should be done on background thread or new thread or io thread
            .observeOn(AndroidSchedulers.mainThread()) // this basically tell that result should come back to main thread.
            .subscribe(object : DisposableSingleObserver<List<Country>>() {

                override fun onSuccess(countryList: List<Country>) {
                    Log.d(TAG, "onSuccessCalled")
                    val countriesList = ArrayList<String>()
                    for (country in countryList) {
                        countriesList.add(country.countryName!!)
                    }
                    countries.value = countriesList
                    countryError.value = false

                }

                override fun onError(e: Throwable) {
                   countryError.value = true
                }

            })
    }

    fun refreshCountries() {
        fetchCountries()
    }

}