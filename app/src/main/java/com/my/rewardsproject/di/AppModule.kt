package com.my.rewardsproject.di

import com.google.gson.Gson
import com.my.rewardsproject.auth.AuthService
import com.my.rewardsproject.business.AppRepository
import com.my.rewardsproject.business.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    // Provides a singleton instance of OkHttpClient
    @Singleton
    @Provides
    fun getOkHttpInstance(): OkHttpClient {
        // Creates an interceptor to log HTTP request and response bodies
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val interceptor = httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Builds and returns the OkHttpClient instance with the logging interceptor attached
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    // Provides a singleton instance of Retrofit, using OkHttpClient and GsonConverterFactory
    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient): AuthService {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/") // Base URL for network calls
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON response into objects
            .client(okHttpClient) // Attaches the provided OkHttpClient
            .build()
            .create(AuthService::class.java) // Creates and returns the AuthService instance
    }

    // Provides a singleton instance of the repository, which depends on AuthService
    @Singleton
    @Provides
    fun getRepoInstance(authService: AuthService): AppRepositoryImpl {
        return AppRepository(authService) // Passes the AuthService instance to the repository
    }

}
