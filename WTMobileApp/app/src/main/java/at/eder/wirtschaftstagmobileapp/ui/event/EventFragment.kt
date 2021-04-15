package at.eder.wirtschaftstagmobileapp.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.EventController
import at.eder.wirtschaftstagmobileapp.controllers.UserController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.Event
import at.eder.wirtschaftstagmobileapp.models.User
import at.eder.wirtschaftstagmobileapp.models.UserTypes
import at.eder.wirtschaftstagmobileapp.ui.event.EventFragment.OnSpinnerEventsSelected.toggleScrollViewEvent
import at.eder.wirtschaftstagmobileapp.ui.event.EventFragment.OnSpinnerEventsSelected.toggleTxtViewNoEventSelected
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        mainV?.findViewById<FloatingActionButton>(R.id.fab_createEvent)?.setOnClickListener { _ -> createEvent(mainV) }
        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshEvents)?.setOnClickListener { _ -> refreshEvents(mainV) }
        mainV?.findViewById<Button>(R.id.btnSaveEvent)?.setOnClickListener {
            saveEvent(mainV);
        }

        toggleScrollViewEvent(mainV, View.INVISIBLE)
        toggleTxtViewNoEventSelected(mainV, View.VISIBLE)

        var spinnerEventOrganisers = mainV?.findViewById<Spinner>(R.id.spinnerEditEventOrganiser)
        if (spinnerEventOrganisers != null)
        GlobalScope.launch {
            suspend {
                UserController().getAllByUserType(UserTypes.admin,
                        { users ->
                            try {
                                if (users != null) {
                                    val adapter = activity?.let {
                                        ArrayAdapter(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                users
                                        )
                                    }
                                    spinnerEventOrganisers.adapter = adapter
                                    mainV.findViewById<Spinner>(R.id.spinnerEvents)?.onItemSelectedListener = OnSpinnerEventsSelected
                                    refreshEvents(mainV)
                                } else {
                                    val adapter = activity?.let {
                                        ArrayAdapter<String>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                listOf("no users available")
                                        )
                                    }
                                    spinnerEventOrganisers.adapter = adapter
                                }
                                message("Departments sucessfully reloaded")
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

    private fun refreshEvents(view: View?) {
        val spinnerEvents = view?.findViewById<Spinner>(R.id.spinnerEvents)
        if (spinnerEvents != null) {
            GlobalScope.launch {
                EventController().getAll(
                        { events ->
                            try {
                                if (events != null) {
                                    val adapter = activity?.let {
                                        ArrayAdapter<Event>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                events
                                        )
                                    }
                                    spinnerEvents.adapter = adapter
                                } else {
                                    val adapter = activity?.let {
                                        ArrayAdapter<String>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                listOf("no events available")
                                        )
                                    }
                                    spinnerEvents.adapter = adapter
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

    object OnSpinnerEventsSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
        ) {
            toggleScrollViewEvent((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoEventSelected((parentView?.parent as View), View.INVISIBLE)
            fillEventEditFields(parentView?.parent as View, parentView?.getItemAtPosition(position) as Event)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewEvent((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoEventSelected((parentView?.parent as View), View.VISIBLE)
        }

        private fun fillEventEditFields(view: View, event: Event) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewEventEdit)
            scrollView?.findViewById<EditText>(R.id.plainTextEditEventId)?.setText(event.id.toString())
            scrollView?.findViewById<EditText>(R.id.plainTextEditEventLabel)?.setText(event.label)
            scrollView?.findViewById<EditText>(R.id.plainTextEditEventDate)?.setText(event.date)
            var spinnerOrganisers = scrollView?.findViewById<Spinner>(R.id.spinnerEditEventOrganiser)
            if (spinnerOrganisers != null && spinnerOrganisers.adapter != null) {
                var found = false
                var count = 0
                while(count < spinnerOrganisers.adapter.count && !found) {
                       if ((spinnerOrganisers.adapter.getItem(count) as User).id == event.organiser?.id)
                           found = true
                    count++
                }
                if (found)
                    spinnerOrganisers.setSelection(count - 1)
            }
        }

        fun toggleTxtViewNoEventSelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoEventSelected)?.visibility = visible
        }

        fun toggleScrollViewEvent(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewEventEdit)?.visibility = visible
        }
    }

    private fun saveEvent(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewEventEdit)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextEditEventId)?.text.toString().toLong()
        var label = scrollView?.findViewById<EditText>(R.id.plainTextEditEventLabel)?.text.toString()
        var date = scrollView?.findViewById<EditText>(R.id.plainTextEditEventDate)?.text.toString()
        var organiser = scrollView?.findViewById<Spinner>(R.id.spinnerEditEventOrganiser)?.selectedItem as User
        GlobalScope.launch {
            EventController().save(Event(id.toLong(), label, date, organiser),
                    {
                        refreshEvents(view)
                        message("Event '$label' sucessfully saved")
                    },
                    { _, t ->
                        errorMessage(t)
                    })
        }
    }

    private fun createEvent(view: View) {
        findNavController().navigate(R.id.action_nav_event_to_nav_event_create)
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}