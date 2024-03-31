package com.example.passwordmanager

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import java.util.Random

class PasswordGeneratorDialog(
    private val context: FragmentActivity,
    private val password: MutableLiveData<String>
) : DialogFragment() {
    var pass: String = ""
    var length: Int = 5
    var includeUppercase: Boolean = false
    var includeLowercase: Boolean = true
    var includeDigits: Boolean = false
    var includeSpecial: Boolean = false

    fun colorizePassword(password: String): SpannableString {
        val spannableString = SpannableString(password)

        val digitColor = ContextCompat.getColor(context, R.color.digit_color)
        val lowercaseColor = ContextCompat.getColor(context, R.color.lower_letter_color)
        val uppercaseColor = ContextCompat.getColor(context, R.color.upper_letter_color)
        val specialCharColor = ContextCompat.getColor(context, R.color.special_char_color)

        var startIndex = 0
        while (startIndex < password.length) {
            val char = password[startIndex]
            var endIndex = startIndex + 1

            when {
                char.isDigit() -> {
                    while (endIndex < password.length && password[endIndex].isDigit()) {
                        endIndex++
                    }
                    spannableString.setSpan(ForegroundColorSpan(digitColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                char.isUpperCase() -> {
                    while (endIndex < password.length && password[endIndex].isUpperCase()) {
                        endIndex++
                    }
                    spannableString.setSpan(ForegroundColorSpan(uppercaseColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                char.isLowerCase() -> {
                    while (endIndex < password.length && password[endIndex].isLowerCase()) {
                        endIndex++
                    }
                    spannableString.setSpan(ForegroundColorSpan(lowercaseColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                else -> {
                    while (endIndex < password.length && !password[endIndex].isLetterOrDigit()) {
                        endIndex++
                    }
                    spannableString.setSpan(ForegroundColorSpan(specialCharColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            startIndex = endIndex
        }

        return spannableString
    }
    fun generatePassword(
        length: Int,
        includeUppercase: Boolean,
        includeLowercase: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean,
        view : TextView
    ) {
        val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowercaseLetters = "abcdefghijklmnopqrstuvwxyz"
        val digits = "0123456789"
        val specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?/`~"

        val allowedChars = StringBuilder()

        if (includeUppercase) {
            allowedChars.append(uppercaseLetters)
        }

        if (includeLowercase) {
            allowedChars.append(lowercaseLetters)
        }

        if (includeDigits) {
            allowedChars.append(digits)
        }

        if (includeSpecial) {
            allowedChars.append(specialChars)
        }

        val random = Random()
        val password = StringBuilder()

        for (i in 0 until length) {
            val index = random.nextInt(allowedChars.length)
            password.append(allowedChars[index])
        }

        pass = password.toString()
        view.text = colorizePassword(pass)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_password_generator, null)

        val generatedPasswordTextView = view.findViewById<TextView>(R.id.generatedPassword)

        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        seekBar.min = 5
        seekBar.max = 25
        seekBar.progress = 5

        val passwordLengthTextView = view.findViewById<TextView>(R.id.passwordLengthTextView)
        passwordLengthTextView.text = "Password length: 5"

        val includeUppercaseCheckBox = view.findViewById<CheckBox>(R.id.includeUppercaseCheckBox)
        val includeLowercaseCheckBox = view.findViewById<CheckBox>(R.id.includeLowercaseCheckBox)
        includeLowercaseCheckBox.isChecked = true
        val includeDigitsCheckBox = view.findViewById<CheckBox>(R.id.includeDigitsCheckBox)
        val includeSpecialCheckBox = view.findViewById<CheckBox>(R.id.includeSpecialCheckBox)
        val generateButton = view.findViewById<Button>(R.id.generateButton)

        length = seekBar.progress
        includeUppercase = includeUppercaseCheckBox.isChecked
        includeLowercase = includeLowercaseCheckBox.isChecked
        includeDigits = includeDigitsCheckBox.isChecked
        includeSpecial = includeSpecialCheckBox.isChecked

         generatePassword(
            length,
            includeUppercase,
            includeLowercase,
            includeDigits,
            includeSpecial,
             generatedPasswordTextView
        )

        includeUppercaseCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            includeUppercase = isChecked
            generatePassword(
                length,
                includeUppercase,
                includeLowercase,
                includeDigits,
                includeSpecial,
                generatedPasswordTextView
            )
        }
        includeLowercaseCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            includeLowercase = isChecked
            generatePassword(
                length,
                includeUppercase,
                includeLowercase,
                includeDigits,
                includeSpecial,
                generatedPasswordTextView
            )
        }
        includeDigitsCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            includeDigits = isChecked
            generatePassword(
                length,
                includeUppercase,
                includeLowercase,
                includeDigits,
                includeSpecial,
                generatedPasswordTextView
            )
        }
        includeSpecialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            includeSpecial = isChecked
            generatePassword(
                length,
                includeUppercase,
                includeLowercase,
                includeDigits,
                includeSpecial,
                generatedPasswordTextView
            )
        }
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                passwordLengthTextView.text = "Password length: ${progress}"
                length = progress
                generatePassword(
                    length,
                    includeUppercase,
                    includeLowercase,
                    includeDigits,
                    includeSpecial,
                    generatedPasswordTextView
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val builder = AlertDialog.Builder(context)
            .setView(view)

        val dialog = builder.create()

        generateButton.setOnClickListener {
            password.value = pass

            dialog.dismiss()
        }

        return dialog
    }
}
