package it.com.uninsubria.footballteam.fragments

import android.Manifest
import android.Manifest.permission.SEND_SMS
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
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.fragment_chat.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val SMS_PERMISSION_CODE = 100

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

    fun checkAndroidVersion() {
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
                sendSMS()
            }
        } else {
            sendSMS()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            SMS_PERMISSION_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS()
            } else {
                Toast.makeText(this.requireContext(), "SEND_SMS Denied", Toast.LENGTH_SHORT).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
           checkAndroidVersion()
        }
    }

    private fun sendSMS(){
        val obj = SmsManager.getDefault()
        val msg = et_boxMessage.text.toString()
        if(msg.length>130){
            Toast.makeText(this.requireContext(), "inserire massimo 130 caratteri", Toast.LENGTH_SHORT).show()
        }
        //source address = null di default numero di telefono
        obj.sendTextMessage("3882550870", null, msg,null,null)
        Log.i("MSG", "messaggio inviato")
        Toast.makeText(this.requireContext(),"messaggio inviato",Toast.LENGTH_SHORT)
    }
}