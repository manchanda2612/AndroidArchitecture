package sapi.com.androidarchitecture.mvc.controller

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import sapi.com.androidarchitecture.model.CountriesService
import sapi.com.androidarchitecture.model.Country
import sapi.com.androidarchitecture.mvc.view.MvcActivity

/**
 * Controller is basically created by the activity. It's going to call API endpoint and return the
 * result to activity.
 */
class CountriesController(val activity: MvcActivity) {

    val service = CountriesService()
    val TAG  = CountriesController::class.java.simpleName

     fun fetchCountries() {
        /**
         * This is going to return single observable of RxJava.
         * To resolve this we call subscribeOn() that  basically tells the system that is need
         * to be run on background thread
         *
         */
        service.getCountries()
            .subscribeOn(Schedulers.newThread()) // this basically tell that all the computation should be done on background thread
            .observeOn(AndroidSchedulers.mainThread()) // this basically tell that result should come back to main thread.
            .subscribe(object : DisposableSingleObserver<List<Country>>() {

                override fun onSuccess(countryList: List<Country>) {
                    Log.d(TAG, "onSuccessCalled")
                    val countries = ArrayList<String>()
                    for (country in countryList) {
                        countries.add(country.countryName!!)

                    }

                    activity.setValue(countries)
                }

                override fun onError(e: Throwable) {
                    activity.handleError()
                }
            })
    }

    fun refreshCountries() {
        fetchCountries()
    }


}