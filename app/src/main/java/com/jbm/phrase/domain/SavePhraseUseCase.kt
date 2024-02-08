package com.jbm.phrase.domain

import com.jbm.phrase.data.local.entity.PhraseEntity
import com.jbm.phrase.data.repository.PhraseRepositoryImpl
import javax.inject.Inject

class SavePhraseUseCase @Inject constructor(private val phraseRepository: PhraseRepositoryImpl){
    suspend operator fun invoke(phrase: String) =
        phraseRepository.insertPhrase(phraseEntity = PhraseEntity(phrase = phrase))
}
