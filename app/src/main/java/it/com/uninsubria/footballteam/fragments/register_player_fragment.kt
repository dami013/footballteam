package it.com.uninsubria.footballteam.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.register_player_fragment.*
import kotlinx.android.synthetic.main.register_player_fragment.view.*
import java.util.*
import kotlin.collections.HashMap


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class register_player_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val FOTO = 1
    private val ref =
        FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
    private lateinit var auth: FirebaseAuth
    private lateinit var img: Uri



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
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.register_player_fragment, container, false)
        auth = Firebase.auth

        val date = view.findViewById<TextView>(R.id.dataNascita)

        val main = AtletiFragment()

        view.immagine.setOnClickListener {
            openGalleryForImage()
        }
        date.setOnClickListener {
            dataPicker()
        }


        view.register.setOnClickListener {
            onRegisterClick()
            view.register.setOnClickListener {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.mainContainer,
                    main
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return view
    }

    private fun onRegisterClick() {

        val name = nome.text.toString().trim()
        val codFisc = cf.text.toString().trim().uppercase()
        val cogn = cognome.text.toString().trim()
        val dataN = dataNascita.text.toString().trim()
        val cel = phone.text.toString().trim()
        val rol = ruolo.text.toString().trim()
        val cert = certificazione?.text.toString()
            .trim()  //certificazioni e risultati possono essere nulli
        val ris = risultati?.text.toString().trim()

        if (name.isEmpty()) {
            nome.error = "inserire nome"
            return
        }
        if (cogn.isEmpty()) {
            cognome.error = "inserire cognome"
            return
        }
        if (dataN.isEmpty()) {
            dataNascita.error = "inserire data di nascita"
            return
        }
        if (rol.isEmpty()) {
            ruolo.error = "inserire ruolo"
            return
        }
        if (cel.isEmpty()) {
            phone.error = "inserire numero di telefono"
            return
        }

        val TAG = "FirebaseStorageManager"
        val ref =
            FirebaseStorage.getInstance().reference.child("/image/${name}")
        // caricamento
        ref.putFile(img).addOnSuccessListener {
            Log.e(TAG, "OK")
            ref.downloadUrl.addOnSuccessListener {
                Log.e(TAG,"$it")
                saveData(name, cogn, dataN, codFisc, rol, cel, cert, ris,it.toString())
            }
        }.addOnFailureListener {
            Log.e(TAG, "KO")
        }



    }


    private fun saveData(
        name: String,
        cogn: String,
        dataN: String,
        codFisc: String,
        rol: String,
        cel: String,
        cert: String,
        ris: String,
        data: String
    ) {

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val atletiMap = HashMap<String, String>()
        atletiMap["nome"] = name
        atletiMap["cognome"] = cogn
        atletiMap["dataNascita"] = dataN
        atletiMap["codiceFiscale"] = codFisc
        atletiMap["telefono"] = cel
        atletiMap["ruolo"] = rol
        atletiMap["certificazioni"] = cert
        atletiMap["risultati"] = ris
        atletiMap["immagine"] = data
        ref.child(uid).child("Atleti").child(codFisc).setValue(atletiMap)
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, FOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == FOTO) {
            img = data?.data!!
            immagine.setImageURI(img)
        }
    }

    fun dataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(view?.context!!,{
                view, y, m, d ->
                val a = "$d/${m+1}/$y"
                dataNascita.text = a


        },year,month,day).show()


    }






}

