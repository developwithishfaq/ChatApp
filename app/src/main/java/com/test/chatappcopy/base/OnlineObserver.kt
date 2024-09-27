package com.test.chatappcopy.base

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OnlineObserver : DefaultLifecycleObserver {

    private val handler = Handler(Looper.getMainLooper())
    private var isHandlerStopped = false

    private val runnable = {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            FirebaseFirestore.getInstance()
                .collection("Users")
                .document(user.uid)
                .update(
                    mapOf(
                        "lastTimeStamp" to System.currentTimeMillis()
                    )
                )
        }
        updateTimeStamp()
    }

    fun initOnlineObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        isHandlerStopped = false
        updateTimeStamp()
    }

    private fun updateTimeStamp() {
        if (isHandlerStopped.not()) {
            handler.postDelayed(runnable, 2000)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isHandlerStopped = true
        handler.removeCallbacks(runnable)
    }
}