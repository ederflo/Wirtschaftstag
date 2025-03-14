package at.eder.wirtschaftstagmobileapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import at.eder.wirtschaftstagmobileapp.controllers.EventController
import at.eder.wirtschaftstagmobileapp.models.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EventFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerEvents = view.findViewById<Spinner>(R.id.spinnerEvents)
        val txtViewNoEventSelected = view?.findViewById<TextView>(R.id.txtViewNoEventSelected)
        if (txtViewNoEventSelected != null) {
            txtViewNoEventSelected.visibility = View.VISIBLE
        }
        spinnerEvents.onItemSelectedListener = OnSpinnerEventSelected
        refreshEvents(view)
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

    object OnSpinnerEventSelected : OnItemSelectedListener {
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

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}
