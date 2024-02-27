package com.jbm.module.core.data.model

import com.google.gson.annotations.SerializedName

data class GoogleFontsDomain(
    @SerializedName("fonts") val fonts: List<GoogleFontDomain>
)

data class GoogleFontDomain(
    @SerializedName("kind") val kind: String,
    @SerializedName("family") val family: String,
    @SerializedName("category") val category: String,
    @SerializedName("variants") val variants: List<String>,
    @SerializedName("subsets") val subsets: List<String>,
    @SerializedName("version") val version: String,
    @SerializedName("lastModified") val lastModified: String
)
