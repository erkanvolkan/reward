package com.my.rewardsproject.auth

import com.my.rewardsproject.models.RewardsResponse
import retrofit2.http.GET

// Retrofit interface for the API calls
interface AuthService {

    // Defines a GET request to fetch data from the provided URL
    @GET("hiring.json")
    suspend fun getData(): RewardsResponse // Returns the API response as a RewardsResponse object
}