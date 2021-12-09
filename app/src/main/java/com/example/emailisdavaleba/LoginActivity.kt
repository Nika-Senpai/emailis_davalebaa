package com.example.emailisdavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPassword2: EditText
    private lateinit var button: Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        submitListeners()



    }


    private fun init(){
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPassword2 = findViewById(R.id.editTextPassword2)
        button = findViewById(R.id.button)
    }

    private fun submitListeners() {
        button.setOnClickListener {


            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val password2 = editTextPassword2.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "EMPTY!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isDigitsOnly() or (!email.contains("@") or (!email.contains(".")) or (email.length < 10))) {
                Toast.makeText(this, "E-mail is incorrect!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password2 != password) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isValidPasswordFormat(password)){
                Toast.makeText(this, "Password must contain upper and lower letters, symbols and numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "წარმატებულია", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show()
                        }


                    }


            }

        }
    }
    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{9,}" +
                "$");
        return passwordREGEX.matcher(password).matches()
    }
}


