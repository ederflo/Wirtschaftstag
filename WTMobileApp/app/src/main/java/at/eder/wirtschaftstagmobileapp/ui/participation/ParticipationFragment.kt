package at.eder.wirtschaftstagmobileapp.ui.participation

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
import at.eder.wirtschaftstagmobileapp.controllers.ParticipationController
import at.eder.wirtschaftstagmobileapp.models.Participation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ParticipationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_participation, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        val fabCreateParticipation: FloatingActionButton = mainV.findViewById(R.id.fab_createParticipation)
        fabCreateParticipation.setOnClickListener { _ -> createParticipation(mainV) }
        val fabRefreshParticipation: FloatingActionButton = mainV.findViewById(R.id.fab_refreshParticipations)
        fabRefreshParticipation.setOnClickListener { _ -> refreshParticipations(mainV) }

        val spinnerParticipations = mainV.findViewById<Spinner>(R.id.spinnerParticipations)
        val txtViewNoParticipationSelected = mainV?.findViewById<TextView>(R.id.txtViewNoParticipationSelected)
        if (txtViewNoParticipationSelected != null) {
            txtViewNoParticipationSelected.visibility = View.VISIBLE
        }
        spinnerParticipations.onItemSelectedListener = OnSpinnerParticipationSelected
        refreshParticipations(mainV)
    }

    private fun refreshParticipations(view: View) {
        val spinnerParticipations = view.findViewById<Spinner>(R.id.spinnerParticipations)
        if (spinnerParticipations != null) {
            GlobalScope.launch {
                ParticipationController().getAll(
                    { participations ->
                        try {
                            if (participations != null) {
                                val adapter = activity?.let {
                                    ArrayAdapter<Participation>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        participations
                                    )
                                }
                                spinnerParticipations.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter<String>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        listOf("no participations available")
                                    )
                                }
                                spinnerParticipations.adapter = adapter
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

    object OnSpinnerParticipationSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoParticipationSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoParticipationSelected)
            if (txtViewNoParticipationSelected != null) {
                txtViewNoParticipationSelected.visibility = View.INVISIBLE
            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoParticipationSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoParticipationSelected)
            if (txtViewNoParticipationSelected != null) {
                txtViewNoParticipationSelected.visibility = View.VISIBLE
            }
        }
    }

    private fun createParticipation(view: View) {

    }

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}