package com.zombito.exporter.services

import com.zombito.exporter.model.*
import retrofit2.Call
import retrofit2.http.*

interface ThreeCommasApi {

  @GET("/public/api/ver1/bots")
  fun getBots(): Call<List<Bot>>

  @GET("/public/api/ver1/bots/{botId}/deals_stats")
  fun getBotStats(@Path("botId") botId: Int): Call<BotDealStats>


  @GET("/public/api/ver1/accounts")
  fun getAccounts(): Call<List<Account>>

  // GET
  @GET ("/public/api/ver1/grid_bots")
  fun getGridBots(): Call<List<GridBot>>

  @GET("/public/api/ver1/deals")
  fun getDeals(): Call<List<Deal>>
}