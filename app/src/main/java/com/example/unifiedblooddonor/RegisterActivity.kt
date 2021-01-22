package com.example.unifiedblooddonor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private val activity = this@RegisterActivity
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutAddress: TextInputLayout
    private lateinit var textInputLayoutDOB: TextInputLayout
    private lateinit var textInputLayoutBloodType: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextAddress: TextInputEditText
    private lateinit var textInputEditTextDOB: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText
    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView
    private lateinit var auth: FirebaseAuth

    private lateinit var bloodSpinner: AppCompatSpinner
    private var bloodTypes = arrayOf("Select Blood Type", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // initializing the views
        initViews()
        // Spinner init
        spinnterInit()
        // initializing the listeners
        initListeners()
        // initializing the objects
        initObjects()
    }
    /**
     * This method is to initialize views
     */
    private fun initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView)
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutAddress = findViewById(R.id.textInputLayoutAddress)
        textInputLayoutDOB = findViewById(R.id.textInputLayoutDOB)
        textInputLayoutBloodType = findViewById(R.id.textInputLayoutBloodType)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword)
        textInputEditTextName = findViewById(R.id.textInputEditTextName)
        textInputEditTextAddress = findViewById(R.id.textInputEditTextAddress)
        textInputEditTextDOB = findViewById(R.id.textInputEditTextDOB)
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword)
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword)
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister)
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink)
        bloodSpinner = findViewById(R.id.spinnerBloodType)
    }
    private fun spinnterInit(){
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodTypes)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodSpinner.adapter = aa
        bloodSpinner.setSelection(0)
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonRegister.setOnClickListener(this)
        appCompatTextViewLoginLink.setOnClickListener(this)
        bloodSpinner.setOnItemSelectedListener(this)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        auth = Firebase.auth
    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    override fun onClick(v: View) {

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}