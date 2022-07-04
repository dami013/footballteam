package it.com.uninsubria.footballteam.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.jar.Manifest

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invia.setOnClickListener{
            val obj = SmsManager.getDefault()
            val msg = et_boxMessage.text.toString()
            if(msg.length>130){
                Toast.makeText(this.requireContext(), "inserire massimo 130 caratteri", Toast.LENGTH_SHORT).show()
            }
            //source address = null di default numero di telefono
            obj.sendTextMessage("3311070793", null, msg,null,null)
            Log.i("MSG", "messaggio inviato")
        }
    }
}