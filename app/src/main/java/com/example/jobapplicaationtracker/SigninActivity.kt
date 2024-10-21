package com.example.jobapplicaationtracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SigninActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailInputLayout = findViewById(R.id.emailLayout)
        passwordInputLayout = findViewById(R.id.passwordLayout)
        emailEditText = findViewById(R.id.emailEt)
        passwordEditText = findViewById(R.id.passET)
        signInButton = findViewById(R.id.button)
        signUpTextView = findViewById(R.id.textView)

        // Sign-in button click listener
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Validate credentials with stored data in SharedPreferences
                val sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                val storedEmail = sharedPref.getString("email", "")
                val storedPassword = sharedPref.getString("password", "")

                if (email == storedEmail && password == storedPassword) {
                    Toast.makeText(this, "Sign-in successful", Toast.LENGTH_SHORT).show()
                    // Proceed to the next screen after successful sign-in
                    navigateToDashboard()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Sign-up TextView click listener (for users who need to register)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToDashboard() {
//        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
}