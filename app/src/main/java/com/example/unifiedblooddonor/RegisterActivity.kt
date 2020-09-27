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
import com.example.unifiedblooddonor.database.DataManager
import com.example.unifiedblooddonor.helpers.InputValidation
import com.example.unifiedblooddonor.modal.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DataManager
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
        nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        textInputLayoutName = findViewById<TextInputLayout>(R.id.textInputLayoutName)
        textInputLayoutAddress = findViewById<TextInputLayout>(R.id.textInputLayoutAddress)
        textInputLayoutDOB = findViewById<TextInputLayout>(R.id.textInputLayoutDOB)
        textInputLayoutBloodType = findViewById<TextInputLayout>(R.id.textInputLayoutBloodType)
        textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        textInputLayoutConfirmPassword = findViewById<TextInputLayout>(R.id.textInputLayoutConfirmPassword)
        textInputEditTextName = findViewById<TextInputEditText>(R.id.textInputEditTextName)
        textInputEditTextAddress = findViewById<TextInputEditText>(R.id.textInputEditTextAddress)
        textInputEditTextDOB = findViewById<TextInputEditText>(R.id.textInputEditTextDOB)
        textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        textInputEditTextConfirmPassword = findViewById<TextInputEditText>(R.id.textInputEditTextConfirmPassword)
        appCompatButtonRegister = findViewById<AppCompatButton>(R.id.appCompatButtonRegister)
        appCompatTextViewLoginLink = findViewById<AppCompatTextView>(R.id.appCompatTextViewLoginLink)
        bloodSpinner = findViewById<AppCompatSpinner>(R.id.spinnerBloodType)
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
        inputValidation = InputValidation(activity)
        databaseHelper = DataManager(activity)
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonRegister -> postDataToSQLite()
            R.id.appCompatTextViewLoginLink -> finish()
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation.isInputSpinnerFilled(bloodSpinner.selectedItemPosition, textInputLayoutBloodType, getString(R.string.error_message_blood_type))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }
        if (!databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim())) {
            var user = User(name = textInputEditTextName.text.toString().trim(),
                email = textInputEditTextEmail.text.toString().trim(),
                pass = textInputEditTextPassword.text.toString().trim(),
                address = textInputEditTextAddress.text.toString().trim(),
                dob = textInputEditTextDOB.text.toString().trim(),
                btype = bloodSpinner.selectedItem.toString())
            databaseHelper.addUser(user)
            // Snack Bar to show success message that record saved successfully
            val intentRegister = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intentRegister)
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}