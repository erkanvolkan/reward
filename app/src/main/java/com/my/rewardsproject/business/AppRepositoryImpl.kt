package com.my.rewardsproject.business

import com.my.rewardsproject.models.RewardsResponse
import kotlinx.coroutines.flow.Flow

// Interface for the repository implementation
interface AppRepositoryImpl {
    // Suspends the function to get the response and returns a Flow of RewardsResponse
    suspend fun getResponse(): Flow<RewardsResponse>
}