package sapi.com.androidarchitecture.mvp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mvc.*
import sapi.com.androidarchitecture.R
import sapi.com.androidarchitecture.mvp.presenter.CountriesPresenter
import sapi.com.androidarchitecture.mvp.presenter.CountriesPresenter.ViewInterface


/**
 * In this architecture, we basically remove the 1 to 1 direct communication between View and Controller.
 * In MVP, we just say Presenter to Controller.
 * In MVP, we have introduced interface between View and Presenter. Now who so ever view implement
 * that interface will get the data. Presenter(Controller) doesn't have any knowledge or an instance
 * of any view with whom it is interacting and same way with View as well it doesn't have any knowledge or
 * an instance with whom it is interacting.
 *
 * So now for example Multiple view can implement the interface and get the data. Now we don't need
 * to change the code in View and Presenter(Controller) as well.
 *
 * ViewInterface basically provide separation between View and Presenter. Presenter doesn't know which
 * object or class or view is actually going to implement this interface.
 *
 * Important - Presenter doesn't have a link or instance of view but the view(activity) has the
 * strong link to the presenter and this is the problem with MVP.
 * Another problem with Presenter is, The presenter calls the view exactly when it has the values and that's a
 * problem because sometime in your application for instance your user is interacting with your screen
 * right so let's say they are watching a video on you tube or they'are playing a game in a web view.
 * And if your presenter is calling your view straight away to update the interface while the user is doing
 * something you would need to handle that information very specifically in your view.
 * We can handle this situation but it is bit more difficult in this architecture. We are going to resolve this
 * situation in MVVM architecture.
 *
 *
 */
class MvpActivity : AppCompatActivity(), ViewInterface {

    private var arrayAdapter : ArrayAdapter<String>? = null
    private var listValues = ArrayList<String>()
    private val countriesPresenter = CountriesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        setTitle("MVP TITLE")

        countriesPresenter.fetchCountries()


        progressbar.visibility = View.VISIBLE
        arrayAdapter = ArrayAdapter<String>(this, R.layout.adapter_mvc, R.id.txv_adapter_mvc, listValues)
        list_mvc_activity.adapter = arrayAdapter

        list_mvc_activity.setOnItemClickListener { adapterView, view, position, l ->

            Toast.makeText(this, listValues.get(position), Toast.LENGTH_LONG).show()

        }
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MvpActivity::class.java)
        }
    }

    override fun setValue(list : ArrayList<String>) {
        arrayAdapter?.clear()
        listValues.addAll(list)
        progressbar.visibility = View.GONE
        btn_mvc_activity_retry.visibility= View.GONE
        list_mvc_activity.visibility= View.VISIBLE
        arrayAdapter?.notifyDataSetChanged()
    }

    override fun handleError () {
        progressbar.visibility = View.GONE
        btn_mvc_activity_retry.visibility= View.VISIBLE
        list_mvc_activity.visibility= View.GONE
    }

    fun onClickRetry(view: View) {
        progressbar.visibility = View.VISIBLE
        btn_mvc_activity_retry.visibility= View.GONE
        list_mvc_activity.visibility= View.GONE
        countriesPresenter.refreshCountries()
    }
}
