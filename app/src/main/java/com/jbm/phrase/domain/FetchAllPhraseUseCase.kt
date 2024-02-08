package com.jbm.phrase.domain

import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.data.repository.PhraseRepositoryImpl
import javax.inject.Inject

class FetchAllPhraseUseCase @Inject constructor(private val phraseRepository: PhraseRepositoryImpl){
    suspend operator fun invoke(): List<PhraseEntity> = phraseRepository.getAllPhrases()
}
