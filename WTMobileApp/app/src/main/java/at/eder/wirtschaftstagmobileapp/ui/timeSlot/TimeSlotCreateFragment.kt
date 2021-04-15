package at.eder.wirtschaftstagmobileapp.ui.timeSlot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.TimeSlotController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.TimeSlot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TimeSlotCreateFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeslot_create, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewTimeSlotCreate)
        scrollView?.findViewById<EditText>(R.id.plainTextCreateTimeSlotId)?.setText(System.currentTimeMillis().toString())
        mainV?.findViewById<Button>(R.id.btnCreateTimeSlot)?.setOnClickListener {
            createTimeSlot(mainV);
        }
    }

    private fun createTimeSlot(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewTimeSlotCreate)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextCreateTimeSlotId)?.text.toString().toLong()
        var starts = scrollView?.findViewById<EditText>(R.id.plainTextCreateTimeSlotStarts)?.text.toString()
        var ends = scrollView?.findViewById<EditText>(R.id.plainTextCreateTimeSlotEnds)?.text.toString()
        var max = scrollView?.findViewById<EditText>(R.id.plainTextCreateTimeSlotMax)?.text.toString().toInt()
        GlobalScope.launch {
            TimeSlotController().save(TimeSlot(id, starts, ends, max),
                    {
                        findNavController().navigate(R.id.action_nav_timeslot_create_to_nav_timeslot)
                        message("Timeslot $starts - $ends sucessfully created")
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