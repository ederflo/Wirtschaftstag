package at.eder.wirtschaftstagmobileapp.ui.mail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.MailController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.Mail
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mail, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        val fabCreateMail: FloatingActionButton = mainV.findViewById(R.id.fab_createMail)
        fabCreateMail.setOnClickListener { _ -> createMail(mainV) }
        val fabRefreshMail: FloatingActionButton = mainV.findViewById(R.id.fab_refreshMails)
        fabRefreshMail.setOnClickListener { _ -> refreshMails(mainV) }

        val spinnerMails = mainV.findViewById<Spinner>(R.id.spinnerMails)
        val txtViewNoMailSelected = mainV?.findViewById<TextView>(R.id.txtViewNoMailSelected)
        if (txtViewNoMailSelected != null) {
            txtViewNoMailSelected.visibility = View.VISIBLE
        }
        spinnerMails.onItemSelectedListener = OnSpinnerMailSelected
        refreshMails(mainV)
        message("Mails sucessfully reloaded")
    }

    private fun refreshMails(view: View) {
        val spinnerMails = view.findViewById<Spinner>(R.id.spinnerMails)
        if (spinnerMails != null) {
            GlobalScope.launch {
                MailController().getAll(
                    { mails ->
                        try {
                            if (mails != null) {
                                val adapter = activity?.let {
                                    ArrayAdapter<Mail>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        mails
                                    )
                                }
                                spinnerMails.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter<String>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        listOf("no mails available")
                                    )
                                }
                                spinnerMails.adapter = adapter
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

    object OnSpinnerMailSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoMailSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoMailSelected)
            if (txtViewNoMailSelected != null) {
                txtViewNoMailSelected.visibility = View.INVISIBLE
            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoMailSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoMailSelected)
            if (txtViewNoMailSelected != null) {
                txtViewNoMailSelected.visibility = View.VISIBLE
            }
        }
    }

    private fun createMail(view: View) {

    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}