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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompanyCreateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_create, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewCompanyCreate)
        mainV?.findViewById<Button>(R.id.btnCreateCompany)?.setOnClickListener {
            createCompany(mainV);
        }
    }

    private fun createCompany(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewCompanyCreate)
        var name = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyName)?.text.toString()
        var zipTown = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyZipTown)?.text.toString()
        var street = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyStreet)?.text.toString()
        var phone = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyPhone)?.text.toString()
        var email = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyEmail)?.text.toString()
        var replyTo = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyReplyTo)?.text.toString()
        var comments = scrollView?.findViewById<EditText>(R.id.plainTextCreateCompanyComments)?.text.toString()
        GlobalScope.launch {
            CompanyController().save(Company(System.currentTimeMillis(), name, zipTown, street, phone, email, replyTo, comments),
                    {
                        findNavController().navigate(R.id.action_nav_company_create_to_nav_company)
                        message("Company '$name' successfully created")
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