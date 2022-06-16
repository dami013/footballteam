package it.com.uninsubria.footballteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*
import java.util.regex.Pattern


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
        val mail = et_email.text.toString().trim()
        val pw = password.text.toString().trim()
        if (mail.isEmpty()||!isValidEmail(mail)) {
            et_email.error = "Enter email"
            return
        }
        if (pw.isEmpty()||!isValidPassword(pw)) {
            password.error = "Enter password"
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

    private fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isValidPassword(pass: String?): Boolean {
        return pass != null && pass.length >= 4
    }

}