package at.eder.wirtschaftstagmobileapp.ui.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.DepartmentController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.Department
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DepartmentCreateFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_department_create, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewDepartmentCreate)
        mainV?.findViewById<Button>(R.id.btnCreateDepartment)?.setOnClickListener {
            createDepartment(mainV);
        }
    }

    private fun createDepartment(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewDepartmentCreate)
        var label = scrollView?.findViewById<EditText>(R.id.plainTextCreateDepartmentLabel)?.text.toString()
        GlobalScope.launch {
            DepartmentController().save(Department(System.currentTimeMillis(), label),
                    {
                        findNavController().navigate(R.id.action_nav_department_create_to_nav_department)
                        message("Department '$label' sucessfully created")
                    },
                    { _, t ->
                        errorMessage(t)
                    })
        }
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}