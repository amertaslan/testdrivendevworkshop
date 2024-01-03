package com.example.tddworkshop.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tddworkshop.network.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _conversionResult = MutableStateFlow("")

    val conversionResult: StateFlow<String> get() = _conversionResult

    fun convertCurrency() {
        viewModelScope.launch {
            try {
                val response = repository.getLatestRates()
                _conversionResult.emit(response.base)
            } catch (e: Exception) {
                _conversionResult.emit("Error")
            }
        }
    }
}

