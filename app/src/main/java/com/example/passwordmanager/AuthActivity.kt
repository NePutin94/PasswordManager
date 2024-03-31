package com.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor


class AuthActivity : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authenticationCallback: BiometricPrompt.AuthenticationCallback

    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Аутентификация")
            .setSubtitle("Введите ваш пароль")
            .setAllowedAuthenticators(DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
//        initBiometricLogin()
//
//        val loginButton = findViewById<Button>(R.id.loginButton)
//        loginButton.setOnClickListener {
//            checkBiometricSupport()
//        }

    }

//    private fun initBiometricLogin() {
//        authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
//            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                super.onAuthenticationError(errorCode, errString)
//                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                super.onAuthenticationSucceeded(result)
//                Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
//                // Perform your logic, e.g., load the user's data or navigate to the main screen.
//            }
//
//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
//                // Show a message or fallback to password authentication.
//            }
//        }
//        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), authenticationCallback)
//        promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Biometric login")
//            .setSubtitle("Log in using your biometric credential")
//            .setNegativeButtonText("Use account password")
//            .build()
//    }
//
//    private fun checkBiometricSupport() {
//        val biometricManager = BiometricManager.from(this)
//        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                showBiometricPrompt()
//            }
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                Toast.makeText(applicationContext, "No biometric hardware detected", Toast.LENGTH_SHORT).show()
//                // Show a message or fallback to password authentication.
//            }
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                Toast.makeText(applicationContext, "Biometric sensor is temporarily unavailable", Toast.LENGTH_SHORT).show()
//                // Show a message or fallback to password authentication.
//            }
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                Toast.makeText(applicationContext, "No biometric credentials enrolled", Toast.LENGTH_SHORT).show()
//                // Show a message or fallback to password authentication.
//            }
//        }
//    }
//
//    private fun showBiometricPrompt() {
//        biometricPrompt.authenticate(promptInfo)
//    }
}