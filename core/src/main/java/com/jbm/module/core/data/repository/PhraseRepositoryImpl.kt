package com.jbm.module.core.data.repository

import com.jbm.module.core.data.di.DispatcherIO
import com.jbm.module.core.data.model.PhraseDomain
import com.jbm.module.core.data.model.toEntity
import com.jbm.module.core.database.dao.PhraseDao
import com.jbm.module.core.database.model.PhraseEntity
import com.jbm.module.core.database.model.toDomain
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
