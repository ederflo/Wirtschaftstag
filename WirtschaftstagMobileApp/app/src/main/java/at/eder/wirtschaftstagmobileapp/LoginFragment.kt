package at.eder.wirtschaftstagmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

    fun connectToAPI(view: View) {
        APIClient.protocol = view.findViewById<EditText>(R.id.txbAPIProtocol).text.toString()
        APIClient.ip = view.findViewById<EditText>(R.id.txbAPIIP).text.toString()
        APIClient.port = view.findViewById<EditText>(R.id.txbAPIPort).text.toString()
        findNavController().navigate(R.id.loginFragment_to_eventFragment)
    }
}