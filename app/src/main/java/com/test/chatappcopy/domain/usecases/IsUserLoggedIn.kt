package com.test.chatappcopy.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import com.test.chatappcopy.data.util.MyPrefs

class IsUserLoggedIn(
    private val prefs: MyPrefs
) {

    operator fun invoke(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null && prefs.isUserLoggedIn
    }

}