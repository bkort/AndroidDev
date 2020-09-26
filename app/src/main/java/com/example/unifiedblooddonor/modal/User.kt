package com.example.unifiedblooddonor.modal

import java.util.*

//modal class for the user
//This is rushed due to hackathon. COME BACK THROUGH APP AND SECURE IT!
data class User(val id: Int = -1, val email: String, val pass: String, val fname: String, val lname: String, val address: String, val state: String, val zip: Int,
                val dob: String, val btype: String)