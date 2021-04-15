package at.eder.wirtschaftstagmobileapp.ui.timeSlot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.TimeSlotController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.TimeSlot
import at.eder.wirtschaftstagmobileapp.ui.timeSlot.TimeSlotFragment.OnSpinnerTimeSlotsSelected.toggleScrollViewTimeSlot
import at.eder.wirtschaftstagmobileapp.ui.timeSlot.TimeSlotFragment.OnSpinnerTimeSlotsSelected.toggleTxtViewNoTimeSlotSelected
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TimeSlotFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeslot, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        mainV?.findViewById<FloatingActionButton>(R.id.fab_createTimeSlot)?.setOnClickListener { _ -> createTimeSlot(mainV) }
        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshTimeSlots)?.setOnClickListener { _ -> refreshTimeSlots(mainV) }
        mainV?.findViewById<Button>(R.id.btnSaveTimeSlot)?.setOnClickListener {
            saveTimeSlot(mainV);
        }

        toggleScrollViewTimeSlot(mainV, View.INVISIBLE)
        toggleTxtViewNoTimeSlotSelected(mainV, View.VISIBLE)

        mainV.findViewById<Spinner>(R.id.spinnerTimeSlots)?.onItemSelectedListener = OnSpinnerTimeSlotsSelected
        refreshTimeSlots(mainV)
        message("Timeslots successfully loaded")
    }

    private fun refreshTimeSlots(view: View?) {
        val spinnerTimeSlots = view?.findViewById<Spinner>(R.id.spinnerTimeSlots)
        if (spinnerTimeSlots != null) {
            GlobalScope.launch {
                TimeSlotController().getAll(
                        { timeSlots ->
                            try {
                                if (timeSlots != null) {
                                    val adapter = activity?.let {
                                        ArrayAdapter<TimeSlot>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                timeSlots
                                        )
                                    }
                                    spinnerTimeSlots.adapter = adapter
                                } else {
                                    val adapter = activity?.let {
                                        ArrayAdapter<String>(
                                                it,
                                                android.R.layout.simple_spinner_item,
                                                listOf("no timeSlots available")
                                        )
                                    }
                                    spinnerTimeSlots.adapter = adapter
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

    object OnSpinnerTimeSlotsSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
        ) {
            toggleScrollViewTimeSlot((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoTimeSlotSelected((parentView?.parent as View), View.INVISIBLE)
            fillTimeSlotEditFields(parentView?.parent as View, parentView?.getItemAtPosition(position) as TimeSlot)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewTimeSlot((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoTimeSlotSelected((parentView?.parent as View), View.VISIBLE)
        }

        private fun fillTimeSlotEditFields(view: View, timeSlot: TimeSlot) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewTimeSlotEdit)
            scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotId)?.setText(timeSlot.id.toString())
            scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotStarts)?.setText(timeSlot.starts)
            scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotEnds)?.setText(timeSlot.ends)
            scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotMax)?.setText(timeSlot.maxParticipants.toString())
        }

        fun toggleTxtViewNoTimeSlotSelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoTimeSlotSelected)?.visibility = visible
        }

        fun toggleScrollViewTimeSlot(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewTimeSlotEdit)?.visibility = visible
        }
    }

    private fun saveTimeSlot(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewTimeSlotEdit)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotId)?.text.toString().toLong()
        var starts = scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotStarts)?.text.toString()
        var ends = scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotEnds)?.text.toString()
        var max = scrollView?.findViewById<EditText>(R.id.plainTextEditTimeSlotMax)?.text.toString().toInt()
        GlobalScope.launch {
            TimeSlotController().save(TimeSlot(id.toLong(), starts, ends, max),
                    {
                        refreshTimeSlots(view)
                        message("Timeslot $starts - $ends successfully saved")
                    },
                    { _, t ->
                        errorMessage(t)
                    })
        }
    }

    private fun createTimeSlot(view: View) {
        findNavController().navigate(R.id.action_nav_timeslot_to_nav_timeslot_create)
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}