package com.gimnastiar.palindromeapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gimnastiar.palindromeapp.data.local.entity.Palindrome

@Database(entities = [Palindrome::class], version = 1, exportSchema = false)
abstract class PalindromeDatabase : RoomDatabase(){
    abstract fun palindromeDao() : PalindromeDao
}