package com.example.jatayu_sih

import android.content.Context
import android.content.SharedPreferences

class loginStatus (context: Context) {

    private val PREFS_NAME = "MyAppPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val KEY_STATUS = "status"
    private val KEY_TOKEN = "token"
    private val KEY_USER_ID = "userId"
    private val KEY_USER_ROLE = "userRole"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, null)
        set(value) = sharedPreferences.edit().putString(KEY_PASSWORD, value).apply()

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var status: String?
        get() = sharedPreferences.getString(KEY_STATUS, null)
        set(value) = sharedPreferences.edit().putString(KEY_STATUS, value).apply()

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(KEY_TOKEN, value).apply()

    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_ID, value).apply()

    var userRole: String?
        get() = sharedPreferences.getString(KEY_USER_ROLE, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_ROLE, value).apply()
}