package at.eder.wirtschaftstagmobileapp.ui.mail

import android.content.Intent
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.*
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.*
import at.eder.wirtschaftstagmobileapp.ui.mail.MailFragment.OnSpinnerMailEventsSelected.toggleScrollViewMail
import at.eder.wirtschaftstagmobileapp.ui.mail.MailFragment.OnSpinnerMailEventsSelected.toggleTxtViewNoMailSelected
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MailFragment : Fragment() {
    companion object {
        var allPrticipations : List<Participation> = listOf()
        var allCompanies : List<Company> = listOf()
        var selectedAllCompanies : Boolean = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mail, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshMails)?.setOnClickListener { _ -> refreshMails(
            mainV
        ) }
        mainV?.findViewById<Button>(R.id.btnSendMailSelection)?.setOnClickListener {
            sendEmail(mainV);
        }
        mainV?.findViewById<Button>(R.id.btnSelectAllMailCompanies)?.setOnClickListener {
            selectAllMailCompanies(mainV);
        }
        mainV?.findViewById<Button>(R.id.btnUnSelectAllMailCompanies)?.setOnClickListener {
            unSelectAllMailCompanies(mainV);
        }

        toggleScrollViewMail(mainV, View.INVISIBLE)
        toggleTxtViewNoMailSelected(mainV, View.VISIBLE)

        val listViewMailParticipations = mainV?.findViewById<ListView>(R.id.listViewMailParticipations)
        listViewMailParticipations.choiceMode = ListView.CHOICE_MODE_MULTIPLE;

        if (listViewMailParticipations != null)
            GlobalScope.launch {
                suspend {
                    ParticipationController().getAll(
                        { participations ->
                            try {
                                if (participations != null) {
                                    val adapter = activity?.let {
                                        ArrayAdapter<Participation>(
                                            it,
                                            android.R.layout.simple_list_item_multiple_choice,
                                            participations
                                        )
                                    }
                                    allPrticipations = participations
                                    listViewMailParticipations.adapter = adapter
                                } else {
                                    val adapter = activity?.let {
                                        ArrayAdapter<String>(
                                            it,
                                            android.R.layout.simple_list_item_multiple_choice,
                                            listOf("no participations available")
                                        )
                                    }
                                    listViewMailParticipations.adapter = adapter
                                }
                                CompanyController().getAll(
                                    { companies ->
                                        try {
                                            if (companies != null) {
                                                allCompanies = companies
                                                mainV.findViewById<Spinner>(R.id.spinnerMailEvents)?.onItemSelectedListener =
                                                    OnSpinnerMailEventsSelected
                                                refreshMails(mainV)
                                                message("Mails successfully loaded")
                                            }
                                        } catch (ex: Throwable) {
                                            errorMessage(ex)
                                        }
                                    },
                                    { _, t ->
                                        errorMessage(t)
                                    })
                            } catch (ex: Throwable) {
                                errorMessage(ex)
                            }
                        },
                        { _, t ->
                            errorMessage(t)
                        })
                }.invoke()
            }
    }

    private fun refreshMails(view: View?) {
        val spinnerMailEvents = view?.findViewById<Spinner>(R.id.spinnerMailEvents)
        val listViewMailParticipations = view?.findViewById<ListView>(R.id.listViewMailParticipations)
        if (spinnerMailEvents != null && listViewMailParticipations != null) {
            GlobalScope.launch {
                EventController().getAll(
                    { events ->
                        try {
                            if (events != null) {
                                val eventsMutable = events.toMutableList()
                                eventsMutable.add(
                                    0, Event(
                                        -1,
                                        "-- all companies --",
                                        null,
                                        null,
                                        null
                                    )
                                )
                                val adapter = activity?.let {
                                    ArrayAdapter(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        eventsMutable
                                    )
                                }
                                spinnerMailEvents.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        listOf("no evnets available")
                                    )
                                }
                                spinnerMailEvents.adapter = adapter
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

    object OnSpinnerMailEventsSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            toggleScrollViewMail((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoMailSelected((parentView?.parent as View), View.INVISIBLE)
            MailFragment().unSelectAllMailCompanies((parentView?.parent as View))
            val listViewMailParticipations = (parentView?.parent as View)?.findViewById<ListView>(R.id.listViewMailParticipations)
            val eventId = (parentView.findViewById<Spinner>(R.id.spinnerMailEvents).selectedItem as Event).id
            if (eventId == (-1).toLong()) {
                val adapterParticipations = parentView?.findFragment<MailFragment>().activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_list_item_multiple_choice,
                        allCompanies
                    )
                }
                listViewMailParticipations.adapter = adapterParticipations
                selectedAllCompanies = true
            } else {
                val adapterParticipations = parentView?.findFragment<MailFragment>().activity?.let {
                    ArrayAdapter(
                        it,
                        android.R.layout.simple_list_item_multiple_choice,
                        allPrticipations.filter { x -> x.event?.id == eventId }
                    )
                }
                listViewMailParticipations.adapter = adapterParticipations
                selectedAllCompanies = false

            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewMail((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoMailSelected((parentView?.parent as View), View.VISIBLE)
        }

        fun toggleTxtViewNoMailSelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoMailSelected)?.visibility = visible
        }

        fun toggleScrollViewMail(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewMailSelection)?.visibility = visible
        }
    }

    private fun sendEmail(view: View?) {
        val listViewMailCompanies = view?.findViewById<ListView>(R.id.listViewMailParticipations)
        val newSelected : SparseBooleanArray? = listViewMailCompanies?.checkedItemPositions
        var currentEvent : Event? = getCurrentSelectedEvent(view);
        if (currentEvent != null && newSelected != null) {
            if (selectedAllCompanies) {
                var selectedCompanies : MutableList<Company> = mutableListOf()
                for (i in 0 until newSelected.size())
                    if (newSelected.get(i))
                        selectedCompanies.add(listViewMailCompanies?.getItemAtPosition(newSelected.keyAt(i)) as Company)
                GlobalScope.launch {
                    UserController().getAllByUserType(UserTypes.admin,
                        { users ->
                            try {
                                if (users != null) {
                                    val subject = view.findViewById<EditText>(R.id.plainTextWriteMailSubject).text.toString()
                                    val content = view.findViewById<EditText>(R.id.plainTextWriteMailContent).text.toString()
                                    var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
                                    intent.type = "message/rfc822"
                                    intent.putExtra(Intent.EXTRA_EMAIL, allCompanies.map {r -> r.email }?.toTypedArray() )
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                                    intent.putExtra(Intent.EXTRA_TEXT, content)
                                    try {
                                        startActivityForResult(Intent.createChooser(intent, "Sending Mails..."), 69)
                                    }
                                    catch (t: Throwable) {
                                        errorMessage(t)
                                    }
                                }
                            } catch (ex: Throwable) {
                                errorMessage(ex)
                            }
                        },
                        { _, t ->
                            errorMessage(t)
                        })
                }
            } else {
                var selectedParticipations : MutableList<Participation> = mutableListOf()
                for (i in 0 until newSelected.size())
                    if (newSelected.get(i))
                        selectedParticipations.add(listViewMailCompanies?.getItemAtPosition(newSelected.keyAt(i)) as Participation)
                GlobalScope.launch {
                    UserController().getAllByUserType(UserTypes.admin,
                        { users ->
                            try {
                                if (users != null) {
                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                                    val timeFormat = SimpleDateFormat("hh:mm:ss")
                                    val currentDate = dateFormat.format(Date())
                                    val currentTime = timeFormat.format(Date())
                                    val subject = view.findViewById<EditText>(R.id.plainTextWriteMailSubject).text.toString()
                                    val content = view.findViewById<EditText>(R.id.plainTextWriteMailContent).text.toString()
                                    MailController().save(Mail(System.currentTimeMillis(), System.currentTimeMillis().toInt(), users.random(),
                                        selectedParticipations.map { it.responsible } as List<User>, currentDate, currentTime, subject, content, null, null, null),
                                        { mail ->
                                            if (mail != null) {
                                                var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
                                                intent.type = "message/rfc822"
                                                intent.putExtra(Intent.EXTRA_EMAIL, mail.receivers?.map {r -> r.email }?.toTypedArray() )
                                                intent.putExtra(Intent.EXTRA_SUBJECT, mail.subject)
                                                intent.putExtra(Intent.EXTRA_TEXT, mail.content)
                                                try {
                                                    startActivityForResult(Intent.createChooser(intent, "Sending Mails..."), 69)
                                                }
                                                catch (t: Throwable) {
                                                    errorMessage(t)
                                                }
                                            }
                                        },
                                        { _, t ->
                                            errorMessage(t)
                                        })
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
    }

    private fun selectAllMailCompanies(view: View?) {
        val listViewMailCompanies = view?.findViewById<ListView>(R.id.listViewMailParticipations)
        for (i in 0 until listViewMailCompanies?.adapter?.count!!) {
            listViewMailCompanies?.setItemChecked(i, true)
        }
        message("All participations/companies selected")
    }

    private fun unSelectAllMailCompanies(view: View?) {
        val listViewMailParticipations = view?.findViewById<ListView>(R.id.listViewMailParticipations)
        for (i in 0 until listViewMailParticipations?.adapter?.count!!) {
            listViewMailParticipations?.setItemChecked(i, false)
        }
        message("All participations/companies unselected")
    }

    private fun getCurrentSelectedEvent(view: View?): Event? {
        return view?.findViewById<Spinner>(R.id.spinnerMailEvents)?.selectedItem as Event?
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}