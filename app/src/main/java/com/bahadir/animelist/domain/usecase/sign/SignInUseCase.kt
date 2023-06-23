package com.bahadir.animelist.domain.usecase.sign

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.repository.Authenticator
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authenticator: Authenticator) {
    operator fun invoke(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        authenticator.signIn(email, password).collect {
            when (it) {
                is Resource.Success -> {
                    emit(Resource.Success(it.data))
                }

                is Resource.Error -> {
                    emit(Resource.Error(it.message))
                }
            }
        }
    }

}