package com.gimnastiar.palindromeapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gimnastiar.palindromeapp.data.local.entity.Palindrome

@Dao
interface PalindromeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inputData(data: Palindrome)

    @Query("DELETE FROM palindrome WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM palindrome")
    suspend fun getAll(): List<Palindrome>

}