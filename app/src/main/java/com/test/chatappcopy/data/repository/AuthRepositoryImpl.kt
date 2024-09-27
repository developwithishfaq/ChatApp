package com.test.chatappcopy.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun loginUser(email: String, password: String): NetworkResponse<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result != null) {
                if (result.user != null) {
                    NetworkResponse.Success(result.user!!)
                } else {
                    NetworkResponse.Failure("User Not Found")
                }
            } else {
                NetworkResponse.Failure("Something Went Wrong")
            }
        }
    }

    override suspend fun createUserAuth(
        email: String,
        password: String
    ): NetworkResponse<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            if (result != null) {
                if (result.user != null) {
                    NetworkResponse.Success(result.user!!)
                } else {
                    NetworkResponse.Failure("User Not Created")
                }
            } else {
                NetworkResponse.Failure("Something Went Wrong")
            }
        }

    }

    override suspend fun createUserInDb(
        uid: String,
        name: String,
        email: String,
        password: String
    ): NetworkResponse<Boolean> {
        return suspendCancellableCoroutine<NetworkResponse<Boolean>> { cor ->
            firestore.collection("Users")
                .document(uid)
                .set(
                    UserModel(
                        name = name,
                        id = uid,
                        email = email,
                    )
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        cor.resume(
                            NetworkResponse.Success(true)
                        )
                    } else {
                        NetworkResponse.Failure(it.exception?.message ?: "")
                    }
                    cor.cancel()
                }
        }
    }

    override fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun setDisplayName(name: String, uid: String): NetworkResponse<Boolean> {
        return suspendCancellableCoroutine {
            auth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.addOnCompleteListener {
                if (it.isSuccessful) {
                    NetworkResponse.Success(true)
                } else {
                    NetworkResponse.Success(it.exception?.message ?: "")
                }
            }
        }
    }
}