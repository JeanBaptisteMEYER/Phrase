package com.jbm.phrase.domain.repository

import com.jbm.phrase.domain.model.GoogleFontDomain

interface AssetRepository {
    suspend fun getAllGoogleFont(): Result<List<GoogleFontDomain>>
}
