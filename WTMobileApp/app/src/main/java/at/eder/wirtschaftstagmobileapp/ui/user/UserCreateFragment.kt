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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserCreateFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_create, container, false)
    }

    override fun onViewCreated(mainV: View, savedInstanceState: Bundle?) {
        super.onViewCreated(mainV, savedInstanceState)

        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewUserCreate)
        scrollView?.findViewById<EditText>(R.id.plainTextCreateUserId)?.setText(System.currentTimeMillis().toString())
        mainV?.findViewById<Button>(R.id.btnCreateUser)?.setOnClickListener {
            createUser(mainV);
        }


        val adapter = activity?.let {
            ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    UserTypes.values().toList()
            )
        }
        scrollView?.findViewById<Spinner>(R.id.spinnerCreateUserTypes)?.adapter = adapter
    }

    private fun createUser(view: View?) {
        var scrollView = view?.findViewById<ScrollView>(R.id.scrollViewUserCreate)
        var id = scrollView?.findViewById<EditText>(R.id.plainTextCreateUserId)?.text.toString().toLong()
        var userType = scrollView?.findViewById<Spinner>(R.id.spinnerCreateUserTypes)?.selectedItem.toString()
        var name = scrollView?.findViewById<EditText>(R.id.plainTextCreateUserName)?.text.toString()
        var email = scrollView?.findViewById<EditText>(R.id.plainTextCreateUserEmail)?.text.toString()
        var pwdToken = scrollView?.findViewById<EditText>(R.id.plainTextCreateUserPwdToken)?.text.toString()
        GlobalScope.launch {
            UserController().save(User(id, UserTypes.valueOf(userType), name, email, pwdToken),
                    {
                        findNavController().navigate(R.id.action_nav_user_create_to_nav_user)
                        message("User '$name' successfully created")
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