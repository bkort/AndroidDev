package com.example.unifiedblooddonor.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.unifiedblooddonor.modal.User

class DataManager(context: Context)
{
    //database
    private val db: SQLiteDatabase

    private inner class CustomSQLiteOpenHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        //variables for common commands. keeps functions clean
        private val CREATE_USER_TABLE = (
                "CREATE TABLE " + TABLE_USER + "("
                        + UTABLE_COL_ID.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT," + UTABLE_COL_EMAIL.toString() + " TEXT," +
                        UTABLE_COL_PASS.toString() + " TEXT" + UTABLE_COL_NAME.toString() + " TEXT," + UTABLE_COL_ADDRESS.toString() + " TEXT" +
                        UTABLE_COL_DOB.toString() + " TEXT" + UTABLE_COL_BTYPE.toString() + " TEXT" +")"
                )
        private val CREATE_BBANK_TABLE = (
                "CREATE TABLE " + TABLE_BANK + "("
                        + BBTABLE_COL_ID.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT," + BBTABLE_COL_NAME.toString() + " TEXT," +
                        BBTABLE_COL_ADDRESS.toString() + " TEXT," + BBTABLE_COL_PHONE.toString() + " TEXT"  +")"
                )
        private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
        private val DROP_BBANK_TABLE = "DROP TABLE IF EXISTS $TABLE_BANK"

        //create Database
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_USER_TABLE)
            db?.execSQL(CREATE_BBANK_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            //drop tables if exist
            db?.execSQL(DROP_USER_TABLE)
            db?.execSQL(DROP_BBANK_TABLE)

            //create tables again
            onCreate(db)
        }

    }

    init {
        //create instatance of database

        val helper = CustomSQLiteOpenHelper(context)
        db = helper.writableDatabase
    }

    companion object {
        /*
        user table column names
         */
        const val UTABLE_COL_ID = "_id"
        const val UTABLE_COL_EMAIL = "email"
        const val UTABLE_COL_PASS = "pass"
        const val UTABLE_COL_NAME = "name"
        const val UTABLE_COL_ADDRESS = "address"
        const val UTABLE_COL_DOB = "dob"
        const val UTABLE_COL_BTYPE = "bloodtype"

        /*
        bloodbank table Columns
         */
        const val BBTABLE_COL_ID = "_id"
        const val BBTABLE_COL_NAME = "name"
        const val BBTABLE_COL_ADDRESS = "address"
        const val BBTABLE_COL_PHONE = "phoneNum"

        /*
        table names
         */
        const val TABLE_USER = "user"
        const val TABLE_BANK = "bloodbank"
        const val TABLE_HISTORY = "userhistory"

        /*
        private const string
         */
        private const val DB_NAME = "AppManager.db"
        private const val DB_VERSION = 1


    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put(UTABLE_COL_EMAIL, user.email)
        values.put(UTABLE_COL_PASS, user.pass)
        values.put(UTABLE_COL_NAME, user.name)
        values.put(UTABLE_COL_ADDRESS, user.address)
        values.put(UTABLE_COL_DOB, user.dob)
        values.put(UTABLE_COL_BTYPE, user.btype)

        //update row
        db.insert(TABLE_USER, null, values)
    }

    fun updateUser(user: User) {
        val values = ContentValues()

        values.put(UTABLE_COL_EMAIL, user.email)
        values.put(UTABLE_COL_PASS, user.pass)
        values.put(UTABLE_COL_NAME, user.name)
        values.put(UTABLE_COL_ADDRESS, user.address)
        values.put(UTABLE_COL_DOB, user.dob)
        values.put(UTABLE_COL_BTYPE, user.btype)
        // updating row
        db.update(TABLE_USER, values, "$UTABLE_COL_ID = ?",
            arrayOf(user.id.toString()))
    }

    fun deleteUser(user: User) {
        // delete user record by id
        db.delete(TABLE_USER, "$UTABLE_COL_ID = ?",
            arrayOf(user.id.toString()))

    }

    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(UTABLE_COL_ID)
        // selection criteria
        val selection = "$UTABLE_COL_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()

        if (cursorCount > 0) {
            return true
        }
        return false
    }

    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(UTABLE_COL_ID)
        // selection criteria
        val selection = "$UTABLE_COL_EMAIL = ? AND $UTABLE_COL_PASS = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()

        if (cursorCount > 0)
            return true
        return false
    }
}