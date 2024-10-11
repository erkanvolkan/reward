package com.my.rewardsproject.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.rewardsproject.models.RewardsResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository // Injecting the repository using Hilt
) : ViewModel() {

    // A StateFlow to emit UI state changes (initial, loading, success, error)
    private val _rewardsStateFlow: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.INITIAL)
    val rewardFlow: StateFlow<UiStates> get() = _rewardsStateFlow // Public access to the state flow

    // Function to fetch rewards data from the repository
    fun getRewards() {
        viewModelScope.launch { // Launch a coroutine tied to the ViewModel lifecycle
            try {
                // Set the state to LOADING before making the request
                _rewardsStateFlow.value = UiStates.LOADING

                // Collect the response from the repository
                appRepository.getResponse().collectLatest { rewardsResponse ->

                    // Filter out items where the name is null or blank
                    val filteredSortedList = rewardsResponse
                        .filter { !it.name.isNullOrBlank() } // Safely checking for null and blank names
                        .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by listId first, then by name

                    // Group the filtered and sorted list by listId
                    val groupedItems = filteredSortedList.groupBy { it.listId }

                    // Emit the SUCCESS state with grouped rewards data
                    _rewardsStateFlow.value = UiStates.SUCCESS(groupedItems)
                }
            } catch (ex: Exception) {
                // Handle any exceptions and emit the ERROR state with the error message
                _rewardsStateFlow.value = UiStates.ERROR(ex.message ?: "Unknown error")
            }
        }
    }

    // Sealed class to represent different UI states (Initial, Loading, Success, and Error)
    sealed class UiStates {
        data object INITIAL : UiStates() // Initial state when nothing has been loaded
        data object LOADING : UiStates() // State while loading data
        data class SUCCESS(val groupedRewards: Map<Int?, List<RewardsResponseItem>>) : UiStates() // Success state with grouped rewards data
        data class ERROR(val error: String) : UiStates() // Error state with the error message
    }
}