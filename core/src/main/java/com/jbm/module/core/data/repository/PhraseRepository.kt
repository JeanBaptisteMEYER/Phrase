package com.jbm.module.core.data.repository

import com.jbm.module.core.data.model.PhraseDomain

interface PhraseRepository {
    suspend fun getAllPhrases(): List<PhraseDomain>
    suspend fun getPhraseById(id: String): Result<PhraseDomain>
    suspend fun insertPhrase(phraseDomain: PhraseDomain)
    suspend fun insertPhrase(phrase: String)
    suspend fun deletePhrase(phraseDomain: PhraseDomain)
}
