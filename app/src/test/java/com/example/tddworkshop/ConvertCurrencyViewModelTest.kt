package com.example.tddworkshop

import com.example.tddworkshop.network.CurrencyRepository
import com.example.tddworkshop.ui.viewmodel.CurrencyViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tddworkshop.data.CurrencyResponse
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ConvertCurrencyViewModelTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = CurrencyViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearMocks(mockRepository)
    }

    private lateinit var viewModel: CurrencyViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockRepository: CurrencyRepository = mockk()

    @Test
    fun `convertCurrency handles error`() = runBlockingTest {
        // Given
        val expectedResult = "Error"

        // Mock the repository response to throw an error
        coEvery { mockRepository.getLatestRates() } throws IllegalStateException("Test error")

        // When
        viewModel.convertCurrency()

        // Then
        val result = viewModel.conversionResult.value
        assertEquals(expectedResult, result)
    }

    @Test
    fun `convertCurrency emits correct result`() = runBlockingTest {
        // Given
        val expectedResult = "EUR"
        val mockResponse = CurrencyResponse(true, expectedResult)

        // Mock the repository response
        coEvery { mockRepository.getLatestRates() } returns mockResponse

        // When
        viewModel.convertCurrency()

        // Then
        val result = viewModel.conversionResult.value
        assertEquals(expectedResult, result)
    }

    @Test
    fun `convertCurrency handles network error`() = runBlockingTest {
        // Given
        val expectedResult = "Error"

        // Mock the repository response to throw a network-related error
        coEvery { mockRepository.getLatestRates() } throws IOException("Network error")

        // When
        viewModel.convertCurrency()

        // Then
        val result = viewModel.conversionResult.value
        assertEquals(expectedResult, result)
    }
}