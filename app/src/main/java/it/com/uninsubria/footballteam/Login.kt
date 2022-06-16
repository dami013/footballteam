package it.com.uninsubria.footballteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG = "LoginActivity"

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
        val name = et_email.text.toString().trim()
        val pw = password.text.toString().trim()
        if (name.isEmpty()) {
            et_email.error = "Enter email"
            return
        }
        if (pw.isEmpty()) {
            password.error = "Enter password"
            return
        }
        loginUser(name, pw)
    }

    private fun loginUser(name: String, pw: String) {
        auth.signInWithEmailAndPassword(name,pw).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Log.d(TAG, "signInWithEmail:success")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Log.w(TAG,"SignIn failed", task.exception)
                val builder = AlertDialog.Builder(this)
                with(builder)
                {
                    setTitle("Authentication failed")
                    setMessage(task.exception?.message)
                    setPositiveButton("OK", null)
                    show()
                }
            }

        }
    }
}