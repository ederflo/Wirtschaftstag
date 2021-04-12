package at.eder.wirtschaftstagmobileapp.ui.event

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
import at.eder.wirtschaftstagmobileapp.controllers.EventController
import at.eder.wirtschaftstagmobileapp.models.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

        val fabCreateEvent: FloatingActionButton = mainV.findViewById(R.id.fab_createEvent)
        fabCreateEvent.setOnClickListener { _ -> createEvent(mainV) }
        val fabRefreshEvent: FloatingActionButton = mainV.findViewById(R.id.fab_refreshEvents)
        fabRefreshEvent.setOnClickListener { _ -> refreshEvents(mainV) }

        val spinnerEvents = mainV.findViewById<Spinner>(R.id.spinnerEvents)
        val txtViewNoEventSelected = mainV?.findViewById<TextView>(R.id.txtViewNoEventSelected)
        if (txtViewNoEventSelected != null) {
            txtViewNoEventSelected.visibility = View.VISIBLE
        }
        spinnerEvents.onItemSelectedListener = OnSpinnerEventSelected
        refreshEvents(mainV)
    }

    private fun refreshEvents(view: View) {
        val spinnerEvents = view.findViewById<Spinner>(R.id.spinnerEvents)
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
                            errorMessage(view, ex)
                        }
                    },
                    { _, t ->
                        errorMessage(view, t)
                    })
            }
        }
    }

    object OnSpinnerEventSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoEventSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoEventSelected)
            if (txtViewNoEventSelected != null) {
                txtViewNoEventSelected.visibility = View.INVISIBLE
            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoEventSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoEventSelected)
            if (txtViewNoEventSelected != null) {
                txtViewNoEventSelected.visibility = View.VISIBLE
            }
        }
    }

    private fun createEvent(view: View) {

    }

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}