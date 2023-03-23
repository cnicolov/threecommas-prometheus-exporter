package com.zombito.exporter.services

import com.zombito.exporter.model.Account
import com.zombito.exporter.model.BotDealStats
import com.zombito.exporter.model.Bot
import com.zombito.exporter.model.GridBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThreeCommasService @Autowired constructor(val api: ThreeCommasApi) {

    fun getBots() : List<Bot> {
        val response = api.getBots().execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

        return response.body().orEmpty()
    }

    fun getBotStats(botId: Int) : BotDealStats {
        val response = api.getBotStats(botId).execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

       return response.body() as BotDealStats
    }

    fun getAccounts(): List<Account> {
        val response = api.getAccounts().execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

        return response.body() as List<Account>
    }

    fun getGridBots(): List<GridBot> {
        val response = api.getGridBots().execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

        return response.body() as List<GridBot>
    }
}