package com.my.rewardsproject.business

import com.my.rewardsproject.auth.AuthService
import com.my.rewardsproject.models.RewardsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Implementation of the AppRepository that fetches data from the AuthService
class AppRepository @Inject constructor(
    private val authService: AuthService // Injecting AuthService using Hilt
) : AppRepositoryImpl {

    // Override the getResponse method to fetch data from the AuthService
    override suspend fun getResponse(): Flow<RewardsResponse> {
        // Return a flow that emits the data fetched from the AuthService
        return flow {
            emit(authService.getData()) // Emitting the API response using Flow
        }
    }
}