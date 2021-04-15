package at.eder.wirtschaftstagmobileapp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.R
import at.eder.wirtschaftstagmobileapp.controllers.UserController
import at.eder.wirtschaftstagmobileapp.helpers.UiHelper
import at.eder.wirtschaftstagmobileapp.models.User
import at.eder.wirtschaftstagmobileapp.models.UserTypes
import at.eder.wirtschaftstagmobileapp.ui.user.UserFragment.OnSpinnerUsersSelected.toggleScrollViewUser
import at.eder.wirtschaftstagmobileapp.ui.user.UserFragment.OnSpinnerUsersSelected.toggleTxtViewNoUserSelected
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

        mainV?.findViewById<FloatingActionButton>(R.id.fab_createUser)?.setOnClickListener { _ -> createUser(mainV) }
        mainV?.findViewById<FloatingActionButton>(R.id.fab_refreshUsers)?.setOnClickListener { _ -> refreshUsers(mainV) }
        mainV?.findViewById<Button>(R.id.btnSaveUser)?.setOnClickListener {
            saveUser(mainV);
        }

        toggleScrollViewUser(mainV, View.INVISIBLE)
        toggleTxtViewNoUserSelected(mainV, View.VISIBLE)

        mainV.findViewById<Spinner>(R.id.spinnerUsers)?.onItemSelectedListener = OnSpinnerUsersSelected
        refreshUsers(mainV)
        message("Users successfully loaded")
    }

    private fun refreshUsers(view: View?) {
        val spinnerUsers = view?.findViewById<Spinner>(R.id.spinnerUsers)
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
                                errorMessage(ex)
                            }
                        },
                        { _, t ->
                            errorMessage(t)
                        })
            }
        }
    }

    object OnSpinnerUsersSelected : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
        ) {
            toggleScrollViewUser((parentView?.parent as View), View.VISIBLE)
            toggleTxtViewNoUserSelected((parentView?.parent as View), View.INVISIBLE)
            fillUserEditFields(parentView?.parent as View, parentView?.getItemAtPosition(position) as User)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            toggleScrollViewUser((parentView?.parent as View), View.INVISIBLE)
            toggleTxtViewNoUserSelected((parentView?.parent as View), View.VISIBLE)
        }

        private fun fillUserEditFields(view: View, user: User) {
            val scrollView = view.findViewById<ScrollView>(R.id.scrollViewUserEdit)
            scrollView?.findViewById<EditText>(R.id.plainTextEditUserId)?.setText(user.id.toString())
            scrollView?.findViewById<TextView>(R.id.txtViewEditUserTypeDisplay)?.text = user.userType.toString()
            scrollView?.findViewById<EditText>(R.id.plainTextEditUserName)?.setText(user.name)
            scrollView?.findViewById<EditText>(R.id.plainTextEditUserEmail)?.setText(user.email)
            scrollView?.findViewById<EditText>(R.id.plainTextEditUserPwdToken)?.setText(user.pwdToken)
        }

        fun toggleTxtViewNoUserSelected(view: View?, visible: Int) {
            view?.findViewById<TextView>(R.id.txtViewNoUserSelected)?.visibility = visible
        }

        fun toggleScrollViewUser(view: View?, visible: Int) {
            view?.findViewById<ScrollView>(R.id.scrollViewUserEdit)?.visibility = visible
        }
    }

    private fun saveUser(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewUserEdit)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextEditUserId)?.text.toString().toLong()
        var userType = scrollView?.findViewById<TextView>(R.id.txtViewEditUserTypeDisplay)?.text.toString()
        var name = scrollView?.findViewById<EditText>(R.id.plainTextEditUserName)?.text.toString()
        var email = scrollView?.findViewById<EditText>(R.id.plainTextEditUserEmail)?.text.toString()
        var pwdToken = scrollView?.findViewById<EditText>(R.id.plainTextEditUserPwdToken)?.text.toString()
        GlobalScope.launch {
            UserController().save(User(id.toLong(), UserTypes.valueOf(userType), name, email, pwdToken),
                    {
                        refreshUsers(view)
                        message("User '$name' successfully created")
                    },
                    { _, t ->
                        errorMessage(t)
                    })
        }
    }

    private fun createUser(view: View) {
        findNavController().navigate(R.id.action_nav_user_to_nav_user_create)
    }

    private fun message(msg: CharSequence) {
        UiHelper().printMessage(activity, msg)
    }

    private fun errorMessage(t: Throwable) {
        UiHelper().handleErrorMessage(activity, t)
    }
}