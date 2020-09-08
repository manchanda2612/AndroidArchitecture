package sapi.com.androidarchitecture.mvvm.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_mvc.*
import kotlinx.android.synthetic.main.activity_mvc.btn_mvc_activity_retry
import kotlinx.android.synthetic.main.activity_mvc.list_mvc_activity
import kotlinx.android.synthetic.main.activity_mvc.progressbar
import kotlinx.android.synthetic.main.activity_mvvm.*
import sapi.com.androidarchitecture.R
import sapi.com.androidarchitecture.mvvm.viewModel.CountriesViewModel

/**
 * This architecture is introduced by google.
 * We are implementing this feature using Lifecycle component of android.
 * In MVVM, we say ViewModel to Presenter(Controller)
 * In this architecture, the children doesn't have direct reference to the parent, they only have the
 * reference by observables.
 * ViewModel requests the data from Model. The Model of course get the data from remote or local
 * and emits it.
 * The Model is basically reply using an observable. Observable is an object that you can subscribe to.
 * And when something happen the model will notify you.
 * Now, View and ViewModel has the same relation. View request the ViewModel that I need country.
 * ViewModel reply using an observable. Observable emits events that view and listen and can handle or
 * process it whenever it can ot whenever it want.
 *
 * Advantage - There is strong separation between view and viewModel. When you test your view model
 * using unit testing then there is no Android code in ViewModel and when you test your view using UI
 * testing then there is only Android code there.
 *
 * It reduces many glue codes in MVP we have attach or detach to the interface in onCreate and onStop method.
 * but in MVVM this is not required because of observable.
 *
 * Here presenter has to notify the view to change the data into Textview etc. but in MVVM this is not
 * required because of observable.
 *
 * Another Advantage - If user is busy with some information some application in your app. For instance if
 * he is watching video on youtube or seeing something on web view then you don't need to handle the
 * information straight away. So view might be busy but when the view comes around and updates the view model
 * will get the latest information from countries. So that's when it handles the information and not when
 * the view model receives it.
 * So it could happen that the view model receives the information and updates the local states. But the view
 * is busy and it will handle later (may be 5seconds later) and then call the observatory model and then
 * updates all the information that is needed.
 *
 */
class MvvmActivity : AppCompatActivity() {

    private var arrayAdapter: ArrayAdapter<String>? = null
    private var listValues = ArrayList<String>()
    private var countriesViewModel = CountriesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        setTitle("MVVM TITLE")

        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)

        progressbar_mvvm.visibility = View.VISIBLE
        arrayAdapter =
            ArrayAdapter<String>(this, R.layout.adapter_mvc, R.id.txv_adapter_mvc, listValues)
        list_mvvm_activity.adapter = arrayAdapter

        list_mvvm_activity.setOnItemClickListener { adapterView, view, position, l ->
            Toast.makeText(this, listValues.get(position), Toast.LENGTH_LONG).show()
        }

        observeViewModel()

    }

    private fun observeViewModel() {

        countriesViewModel.getCountries().observe(this, countriesObserver)

        countriesViewModel.getError().observe(this, Observer<Boolean> { error ->
            progressbar_mvvm.visibility = View.GONE
            if(error) {
                btn_mvvm_activity_retry.visibility = View.VISIBLE
            } else {
                btn_mvvm_activity_retry.visibility = View.GONE
            }
        })
    }

    val countriesObserver = Observer<List<String>> { countries ->
        // Update the UI
        progressbar_mvvm.visibility = View.GONE
        if (null != countries) {
            listValues.clear()
            listValues.addAll(countries)
            list_mvvm_activity.visibility = View.VISIBLE
            arrayAdapter?.notifyDataSetChanged()
        } else {
            list_mvvm_activity.visibility = View.GONE
        }
    }


    fun onClickRetry(view: View) {
        progressbar_mvvm.visibility = View.VISIBLE
        btn_mvvm_activity_retry.visibility = View.GONE
        list_mvvm_activity.visibility = View.GONE
        countriesViewModel.refreshCountries()
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MvvmActivity::class.java)
        }
    }
}
