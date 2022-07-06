package it.com.uninsubria.footballteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_atleti.*
import kotlinx.android.synthetic.main.login.*


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG = "LoginActivity"
    private val check = CheckEmailPassword()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth

        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_SignIn.setOnClickListener {
            onLoginClick()
        }
    }

    private fun onLoginClick() {
        val mail = et_email.text.toString().trim()
        val pw = password.text.toString().trim()
        if (mail.isEmpty()||!check.isValidEmail(mail)) {
            et_email.error = "Password vuota o non corretta"
            return
        }
        if (pw.isEmpty()||!check.isValidPassword(pw)) {
            password.error = "Password vuota o non corretta"
            return
        }
        loginUser(mail, pw)
    }

    private fun loginUser(name: String, pw: String) {
        auth.signInWithEmailAndPassword(name,pw).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Log.d(TAG, "signInWithEmail:success")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthException) {
                    // email already in use
                    Toast.makeText(applicationContext, "login failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}