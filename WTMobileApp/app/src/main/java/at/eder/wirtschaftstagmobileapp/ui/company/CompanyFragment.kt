package at.eder.wirtschaftstagmobileapp.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.CompanyController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.Company
import at.eder.wirtschaftstagmobileapp.ui.company.CompanyFragment.OnSpinnerCompaniesSelected.toggleScrollViewCompany
import at.eder.wirtschaftstagmobileapp.ui.company.CompanyFragment.OnSpinnerCompaniesSelected.toggleTxtViewNoCompanySelected
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

        mainV?.findViewById<FloatingActionButton>(R.id.fab_createCompany)?.setOnClickListener { _ -> createCompany(mainV) }
        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshCompanies)?.setOnClickListener { _ -> refreshCompanies(mainV) }
        mainV?.findViewById<Button>(R.id.btnSaveCompany)?.setOnClickListener {
            saveCompany(mainV);
        }

        toggleScrollViewCompany(mainV, View.INVISIBLE)
        toggleTxtViewNoCompanySelected(mainV, View.VISIBLE)

        mainV.findViewById<Spinner>(R.id.spinnerCompanies)?.onItemSelectedListener = OnSpinnerCompaniesSelected
        refreshCompanies(mainV)
        message("Companies successfully loaded")
    }

    private fun refreshCompanies(view: View?) {
        val spinnerCompanies = view?.findViewById<Spinner>(R.id.spinnerCompanies)
        if (spinnerCompanies != null) {
            GlobalScope.launch {
                CompanyController().getAll(
                    { companies ->
                        try {
                            if (companies != null) {
                                val adapter = activity?.let {
                                    ArrayAdapter<Company>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        companies
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
                            errorMessage(ex)
                        }
                    },
                    { _, t ->
                        errorMessage(t)
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
            toggleScrollViewCompany((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoCompanySelected((parentView?.parent as View), View.INVISIBLE)
            fillCompanyEditFields(parentView?.parent as View, parentView?.getItemAtPosition(position) as Company)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewCompany((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoCompanySelected((parentView?.parent as View), View.VISIBLE)
        }

        private fun fillCompanyEditFields(view: View, company: Company) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewCompanyEdit)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyId)?.setText(company.id.toString())
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyName)?.setText(company.name)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyZipTown)?.setText(company.zipTown)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyStreet)?.setText(company.street)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyPhone)?.setText(company.phone)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyEmail)?.setText(company.email)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyReplyTo)?.setText(company.replyTo)
            scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyComments)?.setText(company.comments)
        }

        fun toggleTxtViewNoCompanySelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoCompanySelected)?.visibility = visible
        }

        fun toggleScrollViewCompany(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewCompanyEdit)?.visibility = visible
        }
    }

    private fun saveCompany(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewCompanyEdit)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyId)?.text.toString().toLong()
        var name = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyName)?.text.toString()
        var zipTown = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyZipTown)?.text.toString()
        var street = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyStreet)?.text.toString()
        var phone = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyPhone)?.text.toString()
        var email = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyEmail)?.text.toString()
        var replyTo = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyReplyTo)?.text.toString()
        var comments = scrollView?.findViewById<EditText>(R.id.plainTextEditCompanyComments)?.text.toString()
        GlobalScope.launch {
            CompanyController().save(Company(id.toLong(), name, zipTown, street, phone, email, replyTo, comments),
                {
                    message("Company '$name' successfully saved")
                    refreshCompanies(view)
                },
                { _, t ->
                    errorMessage( t)
                })
        }
    }

    private fun createCompany(view: View) {
        findNavController().navigate(R.id.action_nav_company_to_nav_company_create)
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}