package it.com.uninsubria.footballteam.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import it.com.uninsubria.footballteam.R
import kotlinx.android.synthetic.main.register_player_fragment.*
import kotlinx.android.synthetic.main.register_player_fragment.view.*
import java.io.ByteArrayOutputStream


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class register_player_fragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val FOTO = 1
    private val ref = FirebaseDatabase.getInstance("https://footballteam-d5795-default-rtdb.firebaseio.com/").getReference("Users")
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.register_player_fragment, container, false)
        auth = Firebase.auth
         val main = AtletiFragment()

        view.immagine.setOnClickListener{
            openGalleryForImage()
        }

        view.register.setOnClickListener{
            onRegisterClick()
            view.register.setOnClickListener(View.OnClickListener {
                val fragmentManager = parentFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.mainContainer,
                    main
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            })
        }

        return view
    }

    private fun onRegisterClick() {
        val bytearros = ByteArrayOutputStream()
        val name = nome.text.toString().trim()
        val codFisc = cf.text.toString().trim()
        val cogn = cognome.text.toString().trim()
        drawableToBitmap(immagine.drawable)?.compress(Bitmap.CompressFormat.JPEG, 100, bytearros)
        val data = String (bytearros.toByteArray())
        val dataN = dataNascita.text.toString().trim()
        val cel = phone.text.toString().trim()
        val rol = ruolo.text.toString().trim()
        val cert = certificazione.text.toString().trim()  //certificazioni e risultati possono essere nulli
        val ris = risultati.text.toString().trim()

        if(name.isEmpty()){
            nome.error = "inserire nome"
            return
        }
        if(cogn.isEmpty()){
            cognome.error = "inserire cognome"
            return
        }
        if(dataN.isEmpty()){
            dataNascita.error = "inserire data di nascita"
            return
        }
        if(rol.isEmpty()){
            cognome.error = "inserire ruolo"
            return
        }
        if(cel.isEmpty()){
            phone.error = "inserire numero di telefono"
            return
        }

        saveData(name,cogn,dataN,codFisc,rol, cel,cert,ris,data)
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
            immagine.setImageURI(data?.data)// handle chosen image
        }
    }
}