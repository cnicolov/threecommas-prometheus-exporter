package com.zombito.exporter

import com.zombito.exporter.model.Account
import com.zombito.exporter.model.BotDealsStatsEntity
import com.zombito.exporter.model.BotEntity
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily
import org.springframework.beans.factory.annotation.Autowired

class ThreeCommasBotsPrometheusCollector @Autowired constructor(private var service: ThreeCommasService): Collector() {

    private val accountBalanceMetric = GaugeMetricFamily(
        "threecommas_account_balance",
        "How much moneyz we haz in threecommas",
        listOf("account_id", "currency")
    )
    private val botProfitMetric = GaugeMetricFamily(
        "threecommas_realized_bots_profit",
        "3Commas realized bot profit by currency",
        listOf("currency", "bot_id", "bot_name")
    )
    private val unrealizedBotProfitMetric = GaugeMetricFamily(
        "threecommas_unrealized_bots_profit",
        "3Commas unrealized bot profit by currency",
        listOf("currency", "bot_id", "bot_name")
    )
    private val fundsLockedMetric = GaugeMetricFamily( "threecommas_funds_locked_in_bot", "3Commas funds locked in active deals", listOf("currency", "bot_id", "bot_name") )
    private val botActiveDealsMetric = GaugeMetricFamily( "threecommas_bot_active_deals", "3Commas active deals per bot", listOf("bot_id", "bot_name") )

    override fun collect(): MutableList<MetricFamilySamples> {

        service.getAccounts().forEach { account -> collectAccountBalance(account, accountBalanceMetric) }

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
        metricFamilies.add(accountBalanceMetric)
        return metricFamilies
    }

    private fun collectRealizedBotProfit(
        bot: BotEntity,
        botProfitMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealsStatsEntity
    ) {
        val usdProfit = dealsStatsEntity.completedDealsUsdProfit
        val btcProfit = dealsStatsEntity.completedDealsBtcProfit
        botProfitMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), btcProfit)
        botProfitMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), usdProfit)
    }

    private fun collectUnrealizedBotProfit(
        bot: BotEntity,
        botProfitMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealsStatsEntity
    ) {
        val usdProfit = dealsStatsEntity.activeDealsUsdProfit
        val btcProfit = dealsStatsEntity.activeDealsBtcProfit
        botProfitMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), btcProfit)
        botProfitMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), usdProfit)
    }

    private fun collectFundsLockedMetric(
        bot: BotEntity,
        fundsLockedMetric: GaugeMetricFamily,
        dealsStatsEntity: BotDealsStatsEntity
    ) {
        val fundsLockedUsd = dealsStatsEntity.fundsLockedInActiveDeals
        val fundsLockedBtc = dealsStatsEntity.btcFundsLockedInActiveDeals
        fundsLockedMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), fundsLockedUsd)
        fundsLockedMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), fundsLockedBtc)
    }

    private fun collectActiveDeals(bot: BotEntity, activeDealsMetric: GaugeMetricFamily) {
        activeDealsMetric.addMetric(listOf(bot.id.toString(), bot.name), bot.activeDealsCount!!.toDouble())
    }

    private fun collectAccountBalance(account: Account, metric: GaugeMetricFamily) {
        metric.addMetric(listOf(account.id.toString(), "USD"), account.usdAmount)
        metric.addMetric(listOf(account.id.toString(), "BTC"), account.btcAmount)
    }
}
