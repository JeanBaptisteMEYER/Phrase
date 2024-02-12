package com.jbm.phrase.data.repository

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jbm.phrase.di.DispatcherIO
import com.jbm.phrase.domain.model.GoogleFontDomain
import com.jbm.phrase.domain.model.GoogleFontsDomain
import com.jbm.phrase.domain.repository.AssetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val assets: AssetManager,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : AssetRepository {
    override suspend fun getAllGoogleFont(): Result<List<GoogleFontDomain>> {
        return withContext(dispatcherIO) {
            // Read File
            val jsonString: String
            try {
                jsonString = assets.open("google_font_list.json")
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {
                return@withContext Result.failure(ioException)
            }

            // Parse File
            val fontsType = object : TypeToken<GoogleFontsDomain>() {}.type
            val fontList: List<GoogleFontDomain>
            try {
                fontList = gson.fromJson<GoogleFontsDomain>(jsonString, fontsType).fonts
            } catch (jsonSyntaxException: JsonSyntaxException) {
                return@withContext Result.failure(jsonSyntaxException)
            }

            // Return result
            Result.success(fontList)
        }
    }
}
