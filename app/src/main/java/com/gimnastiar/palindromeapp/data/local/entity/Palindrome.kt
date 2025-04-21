package com.gimnastiar.palindromeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "palindrome"
)
data class Palindrome(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isPalindrome: Boolean,
)
