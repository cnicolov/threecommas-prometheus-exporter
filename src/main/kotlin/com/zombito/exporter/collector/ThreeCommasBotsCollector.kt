package com.zombito.exporter.collector

import com.zombito.exporter.model.BotDealStats
import com.zombito.exporter.model.Bot
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily

class ThreeCommasBotsCollector constructor(private var service: ThreeCommasService): Collector() {

    override fun collect(): MutableList<MetricFamilySamples> {

        val botProfitMetric = GaugeMetricFamily("threecommas_realized_bots_profit","3Commas realized bot profit by currency", listOf("currency", "bot_id", "bot_name")
        )
        val unrealizedBotProfitMetric = GaugeMetricFamily("threecommas_unrealized_bots_profit", "3Commas unrealized bot profit by currency", listOf("currency", "bot_id", "bot_name"))
        val fundsLockedMetric = GaugeMetricFamily("threecommas_funds_locked_in_bot", "3Commas funds locked in active deals", listOf("currency", "bot_id", "bot_name"))
        val botActiveDealsMetric = GaugeMetricFamily("threecommas_bot_active_deals", "3Commas active deals per bot", listOf("bot_id", "bot_name"))

        val metricFamilies = mutableListOf<MetricFamilySamples>()
        val bots = service.getBots()

        bots.forEach { bot ->
            val dealStats = service.getBotStats(bot.id as Int)

            collectRealizedBotProfit(bot, botProfitMetric, dealStats)
            collectUnrealizedBotProfit(bot, unrealizedBotProfitMetric, dealStats)
            collectFundsLockedMetric(bot, fundsLockedMetric, dealStats)
            collectActiveDeals(bot, botActiveDealsMetric)
        }

        metricFamilies.add(botProfitMetric)
        metricFamilies.add(unrealizedBotProfitMetric)
        metricFamilies.add(fundsLockedMetric)
        metricFamilies.add(botActiveDealsMetric)

        return metricFamilies
    }

    private fun collectRealizedBotProfit(
        bot: Bot,
        botProfitMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealStats
    ) {
        val usdProfit = dealsStatsEntity.completedDealsUsdProfit
        val btcProfit = dealsStatsEntity.completedDealsBtcProfit
        botProfitMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), btcProfit)
        botProfitMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), usdProfit)
    }

    private fun collectUnrealizedBotProfit(
        bot: Bot,
        botProfitMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealStats
    ) {
        val usdProfit = dealsStatsEntity.activeDealsUsdProfit
        val btcProfit = dealsStatsEntity.activeDealsBtcProfit
        botProfitMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), btcProfit)
        botProfitMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), usdProfit)
    }

    private fun collectFundsLockedMetric(
        bot: Bot,
        fundsLockedMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealStats
    ) {
        val fundsLockedUsd = dealsStatsEntity.fundsLockedInActiveDeals
        val fundsLockedBtc = dealsStatsEntity.btcFundsLockedInActiveDeals
        fundsLockedMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), fundsLockedUsd)
        fundsLockedMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), fundsLockedBtc)
    }

    private fun collectActiveDeals(bot: Bot, activeDealsMetric: GaugeMetricFamily) {
        activeDealsMetric.addMetric(listOf(bot.id.toString(), bot.name), bot.activeDealsCount!!.toDouble())
    }
}