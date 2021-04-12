package at.eder.wirtschaftstagmobileapp.ui.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.DepartmentController
import at.eder.wirtschaftstagmobileapp.models.Department
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

        val fabCreateDepartment: FloatingActionButton = mainV.findViewById(R.id.fab_createDepartment)
        fabCreateDepartment.setOnClickListener { _ -> createDepartment(mainV) }
        val fabRefreshDepartments: FloatingActionButton = mainV.findViewById(R.id.fab_refreshDepartments)
        fabRefreshDepartments.setOnClickListener { _ -> refreshDepartments(mainV) }

        val spinnerDepartments = mainV.findViewById<Spinner>(R.id.spinnerDepartments)
        val txtViewNoDepartmentSelected = mainV?.findViewById<TextView>(R.id.txtViewNoDepartmentSelected)
        if (txtViewNoDepartmentSelected != null) {
            txtViewNoDepartmentSelected.visibility = View.VISIBLE
        }
        spinnerDepartments.onItemSelectedListener = OnSpinnerDepartmentSelected
        refreshDepartments(mainV)
    }

    private fun refreshDepartments(view: View) {
        val spinnerDepartments = view.findViewById<Spinner>(R.id.spinnerDepartments)
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
                            errorMessage(view, ex)
                        }
                    },
                    { _, t ->
                        errorMessage(view, t)
                    })
            }
        }
    }

    object OnSpinnerDepartmentSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoDepartmentSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoDepartmentSelected)
            val scrollView = (parentView?.parent as View)?.findViewById<TextView>(R.id.scrollViewDepartmentEdit);
            if (txtViewNoDepartmentSelected != null)
                txtViewNoDepartmentSelected.visibility = View.INVISIBLE
            if (scrollView != null) {
                scrollView.visibility = View.VISIBLE
                fillDepartmentEditFields(parentView?.parent as View, parentView.getItemAtPosition(position) as Department)
            }
        }

        private fun fillDepartmentEditFields(view: View, department: Department) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewDepartmentEdit)
            scrollView.findViewById<EditText>(R.id.plainTextEditDepartmentId).setText(department.id.toString())
            scrollView.findViewById<EditText>(R.id.plainTextEditDepartmentLabel).setText(department.label)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoDepartmentSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoDepartmentSelected)
            val scrollView = (parentView?.parent as View)?.findViewById<TextView>(R.id.scrollViewDepartmentEdit);
            if (txtViewNoDepartmentSelected != null)
                txtViewNoDepartmentSelected.visibility = View.VISIBLE
            if (scrollView != null)
                scrollView.visibility = View.INVISIBLE
        }
    }

    private fun createDepartment(view: View) {

    }

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}