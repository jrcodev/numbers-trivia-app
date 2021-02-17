package com.jrcodev.numberstrivia

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jrcodev.numberstrivia.network.NumbersApiService
import com.jrcodev.numberstrivia.storage.NumberTrivia
import com.jrcodev.numberstrivia.storage.NumberTriviaDatabase
import com.jrcodev.numberstrivia.storage.NumberTriviaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response



class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val _searchStatus = MutableLiveData<SearchStatus>()
    private val repository = NumberTriviaRepository(NumberTriviaDatabase.getInstance(app.applicationContext).dao)
    val searchStatus: LiveData<SearchStatus>
        get() = _searchStatus

    var triviaList: LiveData<List<NumberTrivia>>

    init {
        _searchStatus.value = SearchStatus.Start
        triviaList = repository.getAllTrivia()
    }

    fun getRandomTrivia() = launchFetch {
        val response = NumbersApiService.getRandomTrivia()
        handleResponse(response)
    }

    fun getNumberTrivia(number: String) = launchFetch {
        val response = NumbersApiService.getNumberTrivia(number)
        handleResponse(response)
    }

    fun insertTrivia(trivia: NumberTrivia){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTrivia(trivia)
        }
    }

    fun deleteTrivia(trivia: NumberTrivia){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrivia(trivia)
        }
    }


    private fun launchFetch(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { _searchStatus.value = SearchStatus.Loading }
                block()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { _searchStatus.value = SearchStatus.Error }
            }
        }
    }

    private suspend fun handleResponse(response: Response<String>) = withContext(Dispatchers.Main) {
        if (response.isSuccessful) {
            val data = response.body()
            Log.i("RESPONSE DATA", "$data")


            if (data.isNullOrEmpty()) {
                _searchStatus.value = SearchStatus.Error
            } else {
                _searchStatus.value = SearchStatus.Result(data)
            }
        } else {
            _searchStatus.value = SearchStatus.Error
        }
    }
}