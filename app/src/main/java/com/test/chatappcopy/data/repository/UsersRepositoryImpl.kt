package com.test.chatappcopy.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.test.chatappcopy.data.model.UserModel
import com.test.chatappcopy.data.network.NetworkResponse
import com.test.chatappcopy.domain.repository.UsersRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UsersRepositoryImpl : UsersRepository {
    override suspend fun getUserDetailById(userId: String): NetworkResponse<UserModel?> {
        val userData = FirebaseFirestore.getInstance()
            .collection("Users")
            .document(userId).get().await()
        return NetworkResponse.Success(userData.toObject(UserModel::class.java))
    }

    override fun getAllUsers(): Flow<NetworkResponse<List<UserModel>>> = callbackFlow {
        val query = FirebaseFirestore.getInstance()
            .collection("Users")
            .whereEqualTo("privateUser", false)
        val listener = query.addSnapshotListener { value, error ->
            if (error == null) {
                if (value != null) {
                    val usersList = value.documents.mapNotNull {
                        it.toObject(UserModel::class.java)
                    }
                    trySend(NetworkResponse.Success(usersList.filter { it.id != FirebaseAuth.getInstance().uid }))
                } else {
                    trySend(NetworkResponse.Failure("Data Not Available"))
                }
            } else {
                trySend(NetworkResponse.Failure(error.message ?: ""))
            }
        }
        awaitClose {
            listener.remove()
        }
    }

}