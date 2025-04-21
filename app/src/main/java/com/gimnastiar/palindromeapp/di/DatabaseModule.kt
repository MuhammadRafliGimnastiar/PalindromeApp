package com.gimnastiar.palindromeapp.di

import android.content.Context
import androidx.room.Room
import com.gimnastiar.palindromeapp.data.local.room.PalindromeDao
import com.gimnastiar.palindromeapp.data.local.room.PalindromeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : PalindromeDatabase {
        return Room.databaseBuilder(
            context,
            PalindromeDatabase::class.java, "palindrome.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: PalindromeDatabase): PalindromeDao = database.palindromeDao()
}