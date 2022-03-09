package com.example.text_detection.data.model

import java.util.Date

/**
 * Alternative for this is just a map, but this looks more kotlin-idiomatic
 *
 * TODO: Docs
 */
sealed class IdData(
    val country: Country
) {
    abstract val firstName: String?
    abstract val middleName: String?
    abstract val lastName: String?
    abstract val birthDate: Date?

    // Can be extended with, for instance, a document expiry date

    /**
     * Implementation for Estonia
     *
     */
    data class Estonia(
        override val firstName: String?,
        override val lastName: String?,
        override val birthDate: Date?,
        val personalCode: String?
    ) : IdData(Country.ESTONIA) {

        override val middleName: String? = null
    }

    /**
     * Just for example
     */
    data class Usa(
        override val firstName: String?,
        override val lastName: String?,
        override val birthDate: Date?
    ) : IdData(Country.USA) {

        override val middleName: String? = null
    }
}
