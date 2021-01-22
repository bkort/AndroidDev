package com.example.unifiedblooddonor

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast


class LoginActivity :  AppCompatActivity(), View.OnClickListener {

    private val activity = this@LoginActivity
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var appCompatButtonForgot: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the view
        setContentView(R.layout.activity_login)

        // initializing the views
        initViews()
        // initializing the listeners
        initListeners()
        // initializing the objects
        auth = Firebase.auth

    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword)
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin)
        appCompatButtonForgot = findViewById(R.id.appCompatButtonForgot)
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister)
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonLogin.setOnClickListener(this)
        appCompatButtonForgot.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
    }

    //check if user is logged in
    public override fun onStart() {
        super.onStart()
        //check if user is signed in
        val currentUser = auth.currentUser

    }

    //sign in attempt
    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }


        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    // move to new page
                    val intentRegister = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intentRegister)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = textInputEditTextEmail.text.toString()
        //check if empty
        if (TextUtils.isEmpty((email))) {
            textInputEditTextEmail.error = "Required."
            valid = false
        } else {
            //needed?
            // textInputEditTextEmail.error = null
        }
        //check if valid form?
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.error = "Valid email Required"
            valid = false
        } else {
            //needed?
            // textInputEditTextEmail.error = null
        }

        var password = textInputEditTextPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            textInputEditTextPassword.error = "Required."
            valid = false
        } else {
            //needed??
            // textInputEditTextPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.appCompatButtonLogin -> signIn(
                textInputEditTextEmail.text.toString(),
                textInputEditTextPassword.text.toString()
            )
            R.id.textViewLinkRegister -> {
                // Navigate to RegisterActivity
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }
}