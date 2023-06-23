package com.bahadir.animelist.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bahadir.animelist.R
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.repository.Authenticator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,

    ) : Authenticator {
    override fun getFirebaseUserUid() = flow {
        firebaseAuth.currentUser?.uid?.let {
            emit(it)
        }
    }

    override fun signUpWithEmailAndPassword(email: String, password: String) = flow {
        try {
            val createUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            //firebaseAuth.currentUser?.sendEmailVerification()?.await()
            emit(Resource.Success(createUser))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun googleSign() = flow {
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id)).requestProfile()
                .requestEmail().build()

            val signInClient = GoogleSignIn.getClient(context, gso).signInIntent

            emit(Resource.Success(signInClient))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun firebaseAuthWithGoogle(idToken: String) = flow {
        try {
            val credential = getCredential(idToken, null)
            emit(Resource.Success(firebaseAuth.signInWithCredential(credential).await()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }

    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun signIn(email: String, password: String) = flow {
        try {
            emit(Resource.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun signInToken(token: String, activity: Activity) = flow {
        firebaseAuth.signInWithCustomToken(token).addOnCompleteListener(activity) {
            Log.e("TAG", "signInToken: ${it.isSuccessful}")
        }.await()
        emit(false)
//        credential.user?.let {
//            emit(true)
//        } ?: emit(false)

    }

    override fun isCurrentUserExist() = flow {
        emit(firebaseAuth.currentUser != null)
    }
}

