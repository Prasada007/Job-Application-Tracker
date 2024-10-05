package com.example.jobapplicaationtracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailInputLayout = findViewById(R.id.emailLayout)
        passwordInputLayout = findViewById(R.id.passwordLayout)
        emailEditText = findViewById(R.id.emailEt)
        passwordEditText = findViewById(R.id.passET)
        signUpButton = findViewById(R.id.button)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save credentials using SharedPreferences
                val sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("email", email)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()

                // Navigate to SigninActivity after successful sign-up
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
