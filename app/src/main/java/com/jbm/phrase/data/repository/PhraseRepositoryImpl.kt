package com.jbm.phrase.data.repository

import com.jbm.phrase.data.local.dao.PhraseDao
import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.data.local.entity.toDomain
import com.jbm.phrase.di.DispatcherIO
import com.jbm.phrase.domain.model.PhraseDomain
import com.jbm.phrase.domain.model.toEntity
import com.jbm.phrase.domain.repository.PhraseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class PhraseRepositoryImpl @Inject constructor(
    private val phraseDao: PhraseDao,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : PhraseRepository {
    override suspend fun getAllPhrases(): List<PhraseDomain> {
        return withContext(dispatcherIO) {
            phraseDao.getAllPhrase().toDomain()
        }
    }

    override suspend fun getPhraseById(id: String): Result<PhraseDomain> {
        return withContext(dispatcherIO) {
            phraseDao.getPhraseById(id)?.let { entity ->
                Result.success(entity.toDomain())
            } ?: run {
                Result.failure(Exception("404 - Phrase Not Found"))
            }
        }
    }

    override suspend fun insertPhrase(phraseDomain: PhraseDomain) {
        withContext(dispatcherIO) {
            phraseDao.insertPhrase(phraseDomain.toEntity())
        }
    }

    override suspend fun insertPhrase(phrase: String) {
        withContext(dispatcherIO) {
            phraseDao.insertPhrase(
                PhraseEntity(
                    phrase = phrase,
                    createdAt = Date()
                )
            )
        }
    }

    override suspend fun deletePhrase(phraseDomain: PhraseDomain) {
        withContext(dispatcherIO) {
            phraseDao.deletePhrase(phraseDomain.toEntity())
        }
    }
}