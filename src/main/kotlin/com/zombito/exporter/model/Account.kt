package com.zombito.exporter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Account(
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonProperty("id") val id: Int,
  @JsonProperty("usd_amount") val usdAmount: Double,
  @JsonProperty("btc_amount") val btcAmount: Double
)