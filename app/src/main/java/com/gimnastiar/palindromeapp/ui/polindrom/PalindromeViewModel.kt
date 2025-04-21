package com.gimnastiar.palindromeapp.ui.polindrom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimnastiar.palindromeapp.data.PalindromeRepository
import com.gimnastiar.palindromeapp.data.local.entity.Palindrome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PalindromeViewModel @Inject constructor(private val repo: PalindromeRepository): ViewModel() {

    fun inputData(text: String, isPalindrome: Boolean) = viewModelScope.launch {
        repo.inputData(text, isPalindrome)
        getAllData()
    }

    private val _data = MutableLiveData<List<Palindrome>>()
    val data: LiveData<List<Palindrome>> get() = _data

    fun getAllData() = viewModelScope.launch {
        _data.value = repo.getAllData()
    }

    fun deleteData(id: Int) = viewModelScope.launch {
        repo.deleteData(id)
        getAllData()
    }
}