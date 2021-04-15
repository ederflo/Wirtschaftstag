package at.eder.wirtschaftstagmobileapp.helpers

import android.widget.Toast
import androidx.fragment.app.FragmentActivity

class UiHelper {

    fun handleErrorMessage(activity: FragmentActivity?, t: Throwable) {
        println(t.message)
        printMessage(activity, t.message!!)
    }

    fun printMessage(activity: FragmentActivity?, msg: CharSequence) {
        if (activity != null)
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}