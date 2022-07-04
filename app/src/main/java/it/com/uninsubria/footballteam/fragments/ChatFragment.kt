package it.com.uninsubria.footballteam.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.fragment_chat.*
import java.io.Serializable

private const val atleti = "lista"
private const val SMS_PERMISSION_CODE = 100

class ChatFragment : Fragment() {

    private var selezionati : ArrayList<Atleta>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        selezionati = args?.getParcelableArrayList(atleti)

    }
    fun checkAndroidVersion(phone : String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.SEND_SMS
            )
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(Manifest.permission.SEND_SMS),
                    SMS_PERMISSION_CODE
                )
                return
            } else {
                sendSMS(phone)
            }
        } else {
            sendSMS(phone)
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
           var str = ""
            for(atl in selezionati!!)
                str += atl.cognome + " "

            tv_destinatari.text = "stai scrivendo a: $str"

            for(atl in selezionati!!)
                checkAndroidVersion(atl.telefono!!)
        }
    }

    private fun sendSMS(phone : String){
        val obj = SmsManager.getDefault()
        val msg = et_boxMessage.text.toString()
        if(msg.length>130){
            Toast.makeText(this.requireContext(), "inserire massimo 130 caratteri", Toast.LENGTH_SHORT).show()
        }
        //source address = null di default numero di telefono
        obj.sendTextMessage(phone, null, msg,null,null)
        Log.i("MSG", "messaggio inviato")
        Toast.makeText(this.requireContext(),"messaggio inviato",Toast.LENGTH_SHORT).show()
    }
}