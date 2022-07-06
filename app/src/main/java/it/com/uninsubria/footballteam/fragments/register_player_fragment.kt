package it.com.uninsubria.footballteam.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import it.com.uninsubria.footballteam.Atleta
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.fragment_atleti.*
import kotlinx.android.synthetic.main.register_player_fragment.*
import kotlinx.android.synthetic.main.register_player_fragment.view.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class register_player_fragment : Fragment() {

    private val FOTO = 1
    private var hasImage = false
    private val ref =
        FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/")
            .getReference("Users")
    private lateinit var auth: FirebaseAuth
    private  var img: Uri? = null
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var codiceFiscale: EditText
    private lateinit var birthDate: TextView
    private lateinit var phoneNumber: EditText
    private lateinit var immagine: ImageView
    private lateinit var posizione: AutoCompleteTextView
    private lateinit var certification: EditText
    private lateinit var results: EditText
    private lateinit var progressBar: ProgressBar



    override fun onResume() {
        super.onResume()
        val ruoliPossibili = resources.getStringArray(R.array.Ruoli)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,ruoliPossibili)
        posizione.setAdapter(arrayAdapter)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.register_player_fragment, container, false)
        auth = Firebase.auth
        immagine = view.findViewById<ImageView>(R.id.immagine)
        name = view.findViewById<EditText>(R.id.nome)
        surname = view.findViewById<EditText>(R.id.cognome)
        codiceFiscale = view.findViewById<EditText>(R.id.cf)
        phoneNumber = view.findViewById<EditText>(R.id.phone)
        posizione = view.findViewById<AutoCompleteTextView>(R.id.ruolo)
        posizione.setDropDownBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this.requireContext(),R.color.golden_yellow)))
        certification = view.findViewById<EditText>(R.id.certificazione)
        birthDate = view.findViewById<TextView>(R.id.dataNascita)
        results = view.findViewById<EditText>(R.id.risultati)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)


        view.immagine.setOnClickListener {
            openGalleryForImage()
        }
        birthDate.setOnClickListener {
            dataPicker()
        }

        view.register.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            onRegisterClick()
        }
        return view
    }

    private fun onRegisterClick() {
        var check = true
        if(!checkImage()) {
            check = false
        }
        if (!checkName()) {
            name.error = "inserire nome"
            check = false
        }
        if (!checkSurname()) {
            cognome.error = "inserire cognome"
            check = false
        }
        if (posizione.text.isEmpty()) {
            Log.d("Ruolo", posizione.text.toString())
            check = false
        }

        Log.d("Ruolo", codiceFiscale.text.toString())
        if(codiceFiscale.text.isEmpty()||codiceFiscale.text.length!=16) {
            cf.error = "codice fiscale non corretto o inesistente"
            check = false
        }

        if (!checkPhone()) {
            phone.error = "inserire numero di telefono"
            check = false
        }

        if(!checkCertficazione()) {
            certificazione.error = "inserire certificazione"
            check = false
        }

        if(!checkResults()) {
            risultati.error = "inserire risultati ottenuti"
            check = false
        }


        val TAG = "FirebaseStorageManager"
        val ref =
            FirebaseStorage.getInstance().reference.child("/image/${name}")

        // caricamento dell'immagine

        if(check) {
            thread(start=true){
                ref.putFile(img!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
                            Log.e(TAG,"$it")

                                    saveData(
                                        name.text.toString(), surname.text.toString(),
                                        birthDate.text.toString(), codiceFiscale.text.toString().uppercase(),
                                        posizione.text.toString().lowercase(), phoneNumber.text.toString(),
                                        certification.text.toString(),
                                        results.text.toString(), it.toString()
                                    )
                                    Log.d(TAG, "Giocatore aggiunto con successo")
                                    progressBar.visibility = View.INVISIBLE

                                }

                                val main = AtletiFragment()
                                creazioneFragment(main)
                            }.addOnFailureListener {
                            Log.e(TAG, "KO")
                        }
                }
        }
        else {
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun checkImage(): Boolean {
        if(img == null) {
            Toast.makeText(view?.context,"Immagine non inserita",Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }

    private fun saveData(name: String, cogn: String, dataN: String, codFisc: String, rol: String,
                         cel: String, cert: String, ris: String, data: String
    ) {

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val atleta = Atleta(name,cogn,dataN,codFisc,rol,cel,cert,data,ris)
        ref.child(uid).child("Atleti").child(codFisc).setValue(atleta)
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
            hasImage=true;
        }
    }

    private fun dataPicker() {
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

    private fun checkPhone(): Boolean {
        if(phoneNumber.text.isEmpty()){
            return false
        } else return phoneNumber.text.length == 10
    }

    private fun checkSurname(): Boolean {
        return !surname.text.isEmpty()
    }

    private fun checkName(): Boolean {
        return !name.text.isEmpty()
    }
    private fun checkResults(): Boolean {
        return !results.text.isEmpty()
    }
    private fun checkCertficazione(): Boolean {
        return !certification.text.isEmpty()
    }
    private fun creazioneFragment(fragment: Fragment) =
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            addToBackStack(null)
            commit()
        }
}
