/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.zombito.exporter.model


import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Values: cross, not_specified, isolated
 *
 * Values: cross,notSpecified,isolated
 */

enum class LeverageType(val value: kotlin.String) {

    @JsonProperty(value = "cross")
    cross("cross"),

    @JsonProperty(value = "not_specified")
    notSpecified("not_specified"),

    @JsonProperty(value = "isolated")
    isolated("isolated");

    /**
     * Override toString() to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): String = value

    companion object {
        /**
         * Converts the provided [data] to a [String] on success, null otherwise.
         */
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is LeverageType) "$data" else null

        /**
         * Returns a valid [LeverageType] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): LeverageType? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}
