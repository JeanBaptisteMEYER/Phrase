package com.jbm.phrase.domain.usecase

import com.jbm.phrase.data.repository.PhraseRepositoryImpl
import com.jbm.phrase.domain.model.PhraseDomain
import com.jbm.phrase.domain.model.toEntity
import java.util.Date
import javax.inject.Inject

class SavePhraseUseCase @Inject constructor(private val phraseRepository: PhraseRepositoryImpl){
    suspend operator fun invoke(phrase: String) =
        phraseRepository.insertPhrase(
            phraseEntity = PhraseDomain(
                phrase = phrase,
                lastAdded = Date()
            ).toEntity()
        )
}
