package com.bahadir.animelist.domain.usecase.activity

import com.bahadir.animelist.domain.repository.Authenticator
import javax.inject.Inject

class IsCurrentUserExistUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    operator fun invoke() = authenticator.isCurrentUserExist()
}
