package com.zombito.exporter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Deal(
//  created_at: 2018-08-08 08:08:08
// updated_at: 2018-09-09 09:09:09
// closed_at: 2018-10-10 10:10:10
// finished?:
  // bot_id
  //
  @JsonProperty("bot_id") val botId: Int,
  @JsonProperty("created_at") val createdAt: java.time.OffsetDateTime,
  @JsonProperty("updated_at") val updatedAt: java.time.OffsetDateTime?,
  @JsonProperty("closed_at") val closedAt: java.time.OffsetDateTime?,
  @JsonProperty("finished") val finished: Boolean,
  @JsonProperty("pair") val pair: String,
)
