package com.example.whereswally.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

}