package com.example.jobapplicaationtracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.jobapplicaationtracker.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay for 3 seconds before moving to SignUpActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish() // Close the SplashScreenActivity
        }, 3000) // 3 seconds delay
    }
}