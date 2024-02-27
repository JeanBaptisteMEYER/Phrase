package com.jbm.module.core.data.repository

import com.jbm.module.core.data.model.GoogleFontDomain

interface AssetRepository {
    suspend fun getAllGoogleFont(): Result<List<GoogleFontDomain>>
}
