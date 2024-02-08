package com.jbm.phrase.extention

/**
 * Remove return from the string
 */
fun String.trimEmptyLines() = trim().replace("\n+".toRegex(), replacement = " ")
