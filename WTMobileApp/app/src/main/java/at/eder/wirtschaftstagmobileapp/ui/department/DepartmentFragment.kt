package at.eder.wirtschaftstagmobileapp.ui.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.DepartmentController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.Department
import at.eder.wirtschaftstagmobileapp.ui.department.DepartmentFragment.OnSpinnerDepartmentsSelected.toggleScrollViewDepartment
import at.eder.wirtschaftstagmobileapp.ui.department.DepartmentFragment.OnSpinnerDepartmentsSelected.toggleTxtViewNoDepartmentSelected
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DepartmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_department, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        mainV?.findViewById<FloatingActionButton>(R.id.fab_createDepartment)?.setOnClickListener { _ -> createDepartment(mainV) }
        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshDepartments)?.setOnClickListener { _ -> refreshDepartments(mainV) }
        mainV?.findViewById<Button>(R.id.btnSaveDepartment)?.setOnClickListener {
            saveDepartment(mainV);
        }

        toggleScrollViewDepartment(mainV, View.INVISIBLE)
        toggleTxtViewNoDepartmentSelected(mainV, View.VISIBLE)

        mainV.findViewById<Spinner>(R.id.spinnerDepartments)?.onItemSelectedListener = OnSpinnerDepartmentsSelected
        refreshDepartments(mainV)
        message("Departments sucessfully reloaded")
    }

    private fun refreshDepartments(view: View?) {
        val spinnerDepartments = view?.findViewById<Spinner>(R.id.spinnerDepartments)
        if (spinnerDepartments != null) {
            GlobalScope.launch {
                DepartmentController().getAll(
                        { departments ->
                            try {
                                if (departments != null) {
                                    val adapter = activity?.let {
                                        ArrayAdapter<Department>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                departments
                                        )
                                    }
                                    spinnerDepartments.adapter = adapter
                                } else {
                                    val adapter = activity?.let {
                                        ArrayAdapter<String>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                listOf("no departments available")
                                        )
                                    }
                                    spinnerDepartments.adapter = adapter
                                }
                            } catch (ex: Throwable) {
                                errorMessage(ex)
                            }
                        },
                        { _, t ->
                            errorMessage( t)
                        })
            }
        }
    }

    object OnSpinnerDepartmentsSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
        ) {
            toggleScrollViewDepartment((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoDepartmentSelected((parentView?.parent as View), View.INVISIBLE)
            fillDepartmentEditFields(parentView?.parent as View, parentView?.getItemAtPosition(position) as Department)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewDepartment((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoDepartmentSelected((parentView?.parent as View), View.VISIBLE)
        }

        private fun fillDepartmentEditFields(view: View, department: Department) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewDepartmentEdit)
            scrollView?.findViewById<EditText>(R.id.plainTextEditDepartmentId)?.setText(department.id.toString())
            scrollView?.findViewById<EditText>(R.id.plainTextEditDepartmentLabel)?.setText(department.label)
        }

        fun toggleTxtViewNoDepartmentSelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoDepartmentSelected)?.visibility = visible
        }

        fun toggleScrollViewDepartment(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewDepartmentEdit)?.visibility = visible
        }
    }

    private fun saveDepartment(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewDepartmentEdit)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextEditDepartmentId)?.text.toString().toLong()
        var label = scrollView?.findViewById<EditText>(R.id.plainTextEditDepartmentLabel)?.text.toString()
        GlobalScope.launch {
            DepartmentController().save(Department(id.toLong(), label),
                    {
                        message("Department '$label' sucessfully saved")
                        refreshDepartments(view)
                    },
                    { _, t ->
                        errorMessage( t)
                    })
        }
    }

    private fun createDepartment(view: View) {
        findNavController().navigate(R.id.action_nav_department_to_nav_department_create)
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}