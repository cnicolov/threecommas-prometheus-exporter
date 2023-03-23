package com.zombito.exporter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.squareup.moshi.Json

@JsonIgnoreProperties(ignoreUnknown = true)
data class GridBot (
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("pair") val pair: String,
  @JsonProperty("account_id") val accountId: Int,
  @JsonProperty("current_price") val currentPrice: Double,
  @JsonProperty("current_profit") val currentProfit: Double,
  @JsonProperty("current_profit_usd") val currentProfitUsd: Double,
  @JsonProperty("total_profit_count") val totalProfitCount: Int,
  @JsonProperty("unrealized_profit_loss") val unrealizedPnl: Double,
  @JsonProperty("current_profit_loss") val currentProfitLoss: Double,
)