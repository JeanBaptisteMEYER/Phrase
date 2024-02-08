package com.jbm.phrase.data.repository

import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhraseRepositoryImpl @Inject constructor(
    private val phraseDao: PhraseDao,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) {
    suspend fun getAllPhrases(): List<PhraseEntity> {
        return withContext(dispatcherIO) {
            phraseDao.getAllPhrase()
        }
    }

    suspend fun getPhraseById(id: String): PhraseEntity? {
        return withContext(dispatcherIO) {
            phraseDao.getPhraseById(id)
        }
    }


    suspend fun insertPhrase(phraseEntity: PhraseEntity) {
        withContext(dispatcherIO) {
            phraseDao.insertPhrase(phraseEntity)
        }
    }

    suspend fun deletePhrase(phraseEntity: PhraseEntity) {
        withContext(dispatcherIO) {
            phraseDao.deletePhrase(phraseEntity)
        }
    }
}
