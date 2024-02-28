package com.jbm.module.core.data.testdouble

import com.jbm.module.core.database.dao.PhraseDao
import com.jbm.module.core.database.model.PhraseEntity

class PhraseDaoTest : PhraseDao {
    private val phraseEntityList = mutableListOf<PhraseEntity>()

    override fun getAllPhrase(): List<PhraseEntity> = phraseEntityList

    override fun getPhraseById(id: String): PhraseEntity? =
        phraseEntityList.firstOrNull { it.id.toString() == id }

    override fun insertPhrase(phraseEntity: PhraseEntity) {
        deletePhrase(phraseEntity)
        phraseEntityList.add(phraseEntity)
    }

    override fun deletePhrase(phraseEntity: PhraseEntity) {
        phraseEntityList.removeIf { it.id == phraseEntity.id }
    }
}
