package com.jbm.module.core.data.repository

import com.jbm.module.core.data.testdouble.PhraseDaoTest
import com.jbm.module.core.database.dao.PhraseDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PhraseRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var subject: PhraseRepository

    private lateinit var phraseDao: PhraseDao


    @BeforeEach
    fun setUp() {
        phraseDao = PhraseDaoTest()

        subject = PhraseRepositoryImpl(
            phraseDao = phraseDao,
            dispatcherIO = dispatcher
        )
    }

    @Test
    fun getAllPhrases() {
        // Given

        // When

        // Then
    }

    @Test
    fun getPhraseById() {
    }

    @Test
    fun insertPhrase() {
    }

    @Test
    fun testInsertPhrase() {
    }

    @Test
    fun deletePhrase() {
    }
}
