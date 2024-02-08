package com.jbm.phrase.domain.usecase

import com.jbm.phrase.data.local.entity.toDomain
import com.jbm.phrase.data.repository.PhraseRepositoryImpl
import com.jbm.phrase.domain.model.PhraseDomain
import javax.inject.Inject

class FetchAllPhraseUseCase @Inject constructor(private val phraseRepository: PhraseRepositoryImpl){
    suspend operator fun invoke(): List<PhraseDomain> = phraseRepository.getAllPhrases().toDomain()
}
