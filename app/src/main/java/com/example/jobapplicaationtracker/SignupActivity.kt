package com.example.jobapplicaationtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var fullNameInputLayout: TextInputLayout
    private lateinit var usernameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var alreadyRegisteredTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth and Database Reference
        firebaseAuth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Job").child("Users")

        // Initialize views
        fullNameInputLayout = findViewById(R.id.fullnameLayout)
        usernameInputLayout = findViewById(R.id.usernameLayout)
        emailInputLayout = findViewById(R.id.emailLayout)
        passwordInputLayout = findViewById(R.id.passwordLayout)
        fullNameEditText = findViewById(R.id.fullnameEt)
        usernameEditText = findViewById(R.id.usernameEt)
        emailEditText = findViewById(R.id.emailEt)
        passwordEditText = findViewById(R.id.passET)
        signUpButton = findViewById(R.id.button)
        progressBar = findViewById(R.id.progressBar)
        alreadyRegisteredTextView = findViewById(R.id.textView)

        // Set click listener for Sign Up button
        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate inputs
            if (!validateInputs(fullName, username, email, password)) return@setOnClickListener

            // Show progress bar
            progressBar.visibility = View.VISIBLE

            // Create user in Firebase Auth
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE // Hide progress bar once completed

                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        val user = mapOf(
                            "email" to email,
                            "fullName" to fullName,
                            "username" to username,
                            "registeredOn" to System.currentTimeMillis()
                        )

                        // Store user data in Firebase Realtime Database
                        userId?.let {
                            databaseRef.child(it).setValue(user)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()
                                        navigateToSigninActivity()
                                    } else {
                                        showToast("Failed to store user data: ${dbTask.exception?.message}")
                                    }
                                }
                        }
                    } else {
                        showToast("Sign-up failed: ${task.exception?.message}")
                    }
                }
        }

        // Click listener for "Already Registered? Sign In!"
        alreadyRegisteredTextView.setOnClickListener {
            navigateToSigninActivity()
        }
    }

    // Validate user inputs
    private fun validateInputs(fullName: String, username: String, email: String, password: String): Boolean {
        // Reset errors initially
        resetErrors()

        return when {
            fullName.isEmpty() -> {
                fullNameInputLayout.error = "Full name is required"
                false
            }
            username.isEmpty() -> {
                usernameInputLayout.error = "Username is required"
                false
            }
            email.isEmpty() -> {
                emailInputLayout.error = "Email is required"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailInputLayout.error = "Please enter a valid email"
                false
            }
            password.isEmpty() -> {
                passwordInputLayout.error = "Password is required"
                false
            }
            password.length < 6 -> {
                passwordInputLayout.error = "Password must be at least 6 characters"
                false
            }
            else -> true
        }
    }

    // Reset input layout errors
    private fun resetErrors() {
        fullNameInputLayout.error = null
        usernameInputLayout.error = null
        emailInputLayout.error = null
        passwordInputLayout.error = null
    }

    // Navigate to Sign-in Activity
    private fun navigateToSigninActivity() {
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
        finish() // Prevent user from going back to the sign-up screen
    }

    // Show toast message
    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
