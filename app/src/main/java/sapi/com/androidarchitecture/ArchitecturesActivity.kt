package sapi.com.androidarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import sapi.com.androidarchitecture.mvc.view.MvcActivity
import sapi.com.androidarchitecture.mvp.view.MvpActivity
import sapi.com.androidarchitecture.mvvm.view.MvvmActivity

/**
 * Qus : Why do we need Android Architecture? why we just program it as we see it fit?
 * Ans : Over the long term code will become complex and difficult to maintain.
 *       New Functionality will be difficult to develop.
 *       It will impact existing functionality either breaking it out or making it slow to run.
 *       In addition new developer coming into the team will have a hard time understanding the
 *       existing functionality.
 *
 * Qus : What does an Architecture Achieve?
 * Ans : It makes code scalable
 *       It makes code testable
 *       Makes code easier to understand
 *
 */
class ArchitecturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architectures)

    }

    fun onClickMVC(view : View) = startActivity(MvcActivity.getIntent(this))

    fun onClickMVP(view : View) = startActivity(MvpActivity.getIntent(this))

    fun onClickMVVM(view : View) = startActivity(MvvmActivity.getIntent(this))

}
