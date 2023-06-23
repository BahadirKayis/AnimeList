package com.bahadir.animelist.domain.repository

import android.app.Activity
import android.content.Intent
import com.bahadir.animelist.common.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface Authenticator {
    fun getFirebaseUserUid(): Flow<String>
    fun signUpWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>
    fun googleSign(): Flow<Resource<Intent>>
    fun firebaseAuthWithGoogle(idToken: String): Flow<Resource<AuthResult>>
    fun signOut()
    fun signIn(email: String, password: String): Flow<Resource<AuthResult>>
    fun signInToken(token: String, activity: Activity): Flow<Boolean>
    fun isCurrentUserExist(): Flow<Boolean>
}