package at.eder.wirtschaftstagmobileapp.ui.loginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<EditText>(R.id.txbAPIProtocol).setText(APIClient.protocol)
        view.findViewById<EditText>(R.id.txbAPIIP).setText(APIClient.ip)
        view.findViewById<EditText>(R.id.txbAPIPort).setText(APIClient.port)

        view.findViewById<Button>(R.id.btnConnect).setOnClickListener {
            connectToAPI(view);
        }
    }

    private fun connectToAPI(view: View) {
        APIClient.protocol = view.findViewById<EditText>(R.id.txbAPIProtocol).text.toString()
        APIClient.ip = view.findViewById<EditText>(R.id.txbAPIIP).text.toString()
        APIClient.port = view.findViewById<EditText>(R.id.txbAPIPort).text.toString()
        findNavController().navigate(R.id.action_nav_login_to_nav_event)
    }
}