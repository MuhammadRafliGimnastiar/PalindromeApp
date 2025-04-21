package com.gimnastiar.palindromeapp.data

import com.gimnastiar.palindromeapp.data.local.entity.Palindrome
import com.gimnastiar.palindromeapp.data.local.room.PalindromeDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PalindromeRepository @Inject constructor(
    private val dao: PalindromeDao
) {

    suspend fun inputData(text: String, isPalindrome: Boolean) {
        val data = Palindrome(
            id = 0,
            text = text,
            isPalindrome = isPalindrome
        )

        dao.inputData(data)
    }

    suspend fun getAllData() = dao.getAll()

    suspend fun deleteData(id: Int) = dao.deleteById(id)
}