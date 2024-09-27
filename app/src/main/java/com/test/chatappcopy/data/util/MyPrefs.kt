package com.test.chatappcopy.data.util

import android.content.Context

class MyPrefs(
    private val context: Context
) {
    private val pref = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)

    var isUserLoggedIn: Boolean
        get() = pref.getBoolean("isUserLoggedIn", false)
        set(value) = pref.edit().putBoolean("isUserLoggedIn", value).apply()
}