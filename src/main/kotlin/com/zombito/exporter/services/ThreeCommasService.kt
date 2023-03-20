package com.zombito.exporter.services

import com.zombito.exporter.model.Account
import com.zombito.exporter.model.BotDealsStatsEntity
import com.zombito.exporter.model.BotEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ThreeCommasService @Autowired constructor(val api: ThreeCommasApi) {

    fun getBots() : List<BotEntity> {
        val response = api.getBots().execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

        return response.body().orEmpty()
    }

    fun getBotStats(botId: Int) : BotDealsStatsEntity {
        val response = api.getBotStats(botId).execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

       return response.body() as BotDealsStatsEntity
    }

    fun getAccounts(): List<Account> {
        val response = api.getAccounts().execute()
        if (!response.isSuccessful) {
            throw Exception("ThreeCommasError: ${response.errorBody()}")
        }

        return response.body() as List<Account>
    }
}