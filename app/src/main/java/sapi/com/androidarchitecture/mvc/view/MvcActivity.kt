package sapi.com.androidarchitecture.mvc.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mvc.*
import sapi.com.androidarchitecture.R
import sapi.com.androidarchitecture.mvc.controller.CountriesController

/**
 * The fundamental unit is the activity. The activity is basically a layout that means it's a view that also contains the business logic.
 * Now these two have been separated in MVC so that the view handle the screen display and business logic handle by the controller.
 * Now there is huge disagreement in the Android community about What MVC is and How it is applied?
 *
 * Now View is basically going to be created first by the Android system and it's gonna create the controller and whenever the view needs to
 * actually display some information it's gonna call the controller. So this is going to be quite direct call here.
 *
 * Problem with this architecture is that there is a clear direct link here between the view and the controller.
 * So view knows exactly which controller objects it has associated and the controller has the same thing
 * so it knows exactly which view it's talking about which basically means that we have a kind of 1 to 1 mapping
 * between View and controller
 * So we don't have a clear separation so if we want to change the view at a later point in the project, we have to
 * actually go into  this class and change that in order to do the updates. We can't just swap if for a new class.
 * So that's the one of the disadvantages of MVC
 *
 * It architecture is better than using adhoc approach to build an android application, in which we usually keep
 * view and business logic in Activity or fragment only. Here we separate the business logic using controller and
 * view using activity or fragment but that view and controller having very strong relationship with each other.
 * So again it's not the best approach to follow.
 *
 * Problem - It has a very tight coupling between the controller and the view.
 *
 */
class MvcActivity : AppCompatActivity() {

    private var arrayAdapter : ArrayAdapter<String>? = null
    private var listValues = ArrayList<String>()
    private val countriesController = CountriesController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)
        setTitle("MVC TITLE")


        countriesController.fetchCountries()


        progressbar.visibility = View.VISIBLE
        arrayAdapter = ArrayAdapter<String>(this, R.layout.adapter_mvc, R.id.txv_adapter_mvc, listValues)
        list_mvc_activity.adapter = arrayAdapter

        list_mvc_activity.setOnItemClickListener { adapterView, view, position, l ->

            Toast.makeText(this, listValues.get(position), Toast.LENGTH_LONG).show()

        }
    }

    fun setValue(list : List<String>) {
        arrayAdapter?.clear()
        listValues.addAll(list)
        progressbar.visibility = View.GONE
        btn_mvc_activity_retry.visibility= View.GONE
        list_mvc_activity.visibility= View.VISIBLE
        arrayAdapter?.notifyDataSetChanged()
    }

    fun handleError () {
        progressbar.visibility = View.GONE
        btn_mvc_activity_retry.visibility= View.VISIBLE
        list_mvc_activity.visibility= View.GONE
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MvcActivity::class.java)
        }
    }

    fun onClickRetry(view: View) {
        progressbar.visibility = View.VISIBLE
        btn_mvc_activity_retry.visibility= View.GONE
        list_mvc_activity.visibility= View.GONE
        countriesController.refreshCountries()
    }

}
