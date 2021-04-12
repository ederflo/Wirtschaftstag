package at.eder.wirtschaftstagmobileapp.ui.user

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
import at.eder.wirtschaftstagmobileapp.controllers.UserController
import at.eder.wirtschaftstagmobileapp.models.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        val fabCreateUser: FloatingActionButton = mainV.findViewById(R.id.fab_createUser)
        fabCreateUser.setOnClickListener { _ -> createUser(mainV) }
        val fabRefreshUser: FloatingActionButton = mainV.findViewById(R.id.fab_refreshUsers)
        fabRefreshUser.setOnClickListener { _ -> refreshUsers(mainV) }

        val spinnerUsers = mainV.findViewById<Spinner>(R.id.spinnerUsers)
        val txtViewNoUserSelected = mainV?.findViewById<TextView>(R.id.txtViewNoUserSelected)
        if (txtViewNoUserSelected != null) {
            txtViewNoUserSelected.visibility = View.VISIBLE
        }
        spinnerUsers.onItemSelectedListener = OnSpinnerUserSelected
        refreshUsers(mainV)
    }

    private fun refreshUsers(view: View) {
        val spinnerUsers = view.findViewById<Spinner>(R.id.spinnerUsers)
        if (spinnerUsers != null) {
            GlobalScope.launch {
                UserController().getAll(
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
                                spinnerUsers.adapter = adapter
                            } else {
                                val adapter = activity?.let {
                                    ArrayAdapter<String>(
                                        it,
                                        android.R.layout.simple_spinner_item,
                                        listOf("no users available")
                                    )
                                }
                                spinnerUsers.adapter = adapter
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

    object OnSpinnerUserSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parentView: AdapterView<*>?,
            selectedItemView: View,
            position: Int,
            id: Long
        ) {
            val txtViewNoUserSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoUserSelected)
            if (txtViewNoUserSelected != null) {
                txtViewNoUserSelected.visibility = View.INVISIBLE
            }
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            val txtViewNoUserSelected = (parentView?.parent as View)?.findViewById<TextView>(R.id.txtViewNoUserSelected)
            if (txtViewNoUserSelected != null) {
                txtViewNoUserSelected.visibility = View.VISIBLE
            }
        }
    }

    private fun createUser(view: View) {

    }

    private fun errorMessage(view: View, t: Throwable) {
        println(t.message)
    }
}