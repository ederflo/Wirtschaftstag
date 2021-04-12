package at.eder.wirtschaftstagmobileapp.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.CompanyController
import at.eder.wirtschaftstagmobileapp.controllers.EventController
import at.eder.wirtschaftstagmobileapp.models.Company
import at.eder.wirtschaftstagmobileapp.models.Department
import at.eder.wirtschaftstagmobileapp.models.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompanyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        val fabCreateCompany: FloatingActionButton = mainV.findViewById(R.id.fab_createCompany)
        fabCreateCompany.setOnClickListener { _ -> createCompany(mainV) }
        val fabRefreshCompanies: FloatingActionButton = mainV.findViewById(R.id.fab_refreshCompanies)
        fabRefreshCompanies.setOnClickListener { _ -> refreshCompanies(mainV) }

        val spinnerCompanies = mainV.findViewById<Spinner>(R.id.spinnerCompanies)
        val txtViewNoCompanySelected = mainV?.findViewById<TextView>(R.id.txtViewNoCompanySelected)
        if (txtViewNoCompanySelected != null) {
            txtViewNoCompanySelected.visibility = View.VISIBLE
        }
        spinnerCompanies.onItemSelectedListener = OnSpinnerCompaniesSelected
        refreshCompanies(mainV)
    }

    private fun refreshCompanies(view: View) {
        val spinnerCompanies = view.findViewById<Spinner>(R.id.spinnerCompanies)
        if (spinnerCompanies != null) {
            GlobalScope.launch {
                CompanyController().getAll(
                    { events ->
                        try {
                            if (events != null) {
                                val adapter = activity?.let {
                                    ArrayAdapter<Company>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        events
                                    )
                                }
                                spinnerCompanies.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter<String>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        listOf("no companies available")
                                    )
                                }
                                spinnerCompanies.adapter = adapter
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

    object OnSpinnerCompaniesSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoCompanySelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoCompanySelected)
            val scrollView = (parentView?.parent as View)?.findViewById<TextView>(R.id.scrollViewDepartmentEdit);
            if (txtViewNoCompanySelected != null)
                txtViewNoCompanySelected.visibility = View.INVISIBLE
            if (scrollView != null) {
                scrollView.visibility = View.VISIBLE
                fillCompanyEditFields(parentView.parent as View, parentView.getItemAtPosition(position) as Company)
            }
        }

        private fun fillCompanyEditFields(view: View, company: Company) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewDepartmentEdit)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyId).setText(company.id.toString())
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyName).setText(company.name)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyZipTown).setText(company.zipTown)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyStreet).setText(company.street)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyStreet).setText(company.phone)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyStreet).setText(company.email)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyStreet).setText(company.replyTo)
            scrollView.findViewById<EditText>(R.id.plainTextEditCompanyStreet).setText(company.comments)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoCompanySelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoCompanySelected)
            if (txtViewNoCompanySelected != null) {
                txtViewNoCompanySelected.visibility = View.VISIBLE
            }
        }
    }

    private fun createCompany(view: View) {

    }

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}