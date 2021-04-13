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
import at.eder.wirtschaftstagmobileapp.models.Event
import at.eder.wirtschaftstagmobileapp.models.User
import at.eder.wirtschaftstagmobileapp.models.UserTypes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventCreateFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_create, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewEventCreate)
        scrollView?.findViewById<EditText>(R.id.plainTextCreateEventId)?.setText(System.currentTimeMillis().toString())
        mainV?.findViewById<Button>(R.id.btnCreateEvent)?.setOnClickListener {
            createEvent(mainV);
        }


        GlobalScope.launch {
            UserController().getAllByUserType(UserTypes.admin,
                    { users ->
                        try {
                            if (users != null) {
                                val adapter = activity?.let {
                                    ArrayAdapter<User>(
                                            it,
                                            android.R.layout.simple_spinner_item,
                                            users
                                    )
                                }
                                mainV?.findViewById<Spinner>(R.id.spinnerCreateEventOrganiser)?.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter<String>(
                                            it,
                                            android.R.layout.simple_spinner_item,
                                            listOf("no users available")
                                    )
                                }
                                mainV?.findViewById<Spinner>(R.id.spinnerCreateEventOrganiser)?.adapter = adapter
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

    private fun createEvent(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewEventCreate)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextCreateEventId)?.text.toString().toLong()
        var label = scrollView?.findViewById<EditText>(R.id.plainTextCreateEventLabel)?.text.toString()
        var date = scrollView?.findViewById<EditText>(R.id.plainTextCreateEventDate)?.text.toString()
        var organiser = scrollView?.findViewById<Spinner>(R.id.spinnerCreateEventOrganiser)?.selectedItem as User
        GlobalScope.launch {
            EventController().save(Event(id, label, date, organiser),
                    {
                        findNavController().navigate(R.id.action_nav_event_create_to_nav_event)
                    },
                    { _, t ->
                        errorMessage(view, t)
                    })
        }
    }

    private fun errorMessage(view: View?, t: Throwable) {
        println(t.message)
    }
}