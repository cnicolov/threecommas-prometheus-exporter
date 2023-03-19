package com.zombito.exporter.services

import com.zombito.exporter.model.BotDealsStatsEntity
import com.zombito.exporter.model.BotEntity
import retrofit2.Call
import retrofit2.http.*

interface ThreeCommasApi {

  @GET("/public/api/ver1/bots")
  fun getBots(): Call<List<BotEntity>>

  @GET("/public/api/ver1/bots/{bot_id}/deals_stats")
  fun getBotStats(@Path("bot_id") botId: Int): Call<BotDealsStatsEntity>

}