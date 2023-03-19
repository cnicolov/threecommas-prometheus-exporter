package com.zombito.exporter

import com.zombito.exporter.model.BotEntity
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.GaugeMetricFamily
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import retrofit2.Response
import java.lang.Exception

class ThreeCommasPrometheusCollector @Autowired constructor(
    private var service: ThreeCommasService,
): Collector() {
    fun collectRealizedBotProfitMetric(bot: BotEntity, botProfitMetric: GaugeMetricFamily) {
        val dealStats = service.getBotStats(bot.id as Int)
        val usdProfit = dealStats?.completedDealsUsdProfit?.toDouble()
        val btcProfit = dealStats?.completedDealsBtcProfit?.toDouble()
        botProfitMetric.addMetric(listOf("BTC", bot.id.toString(), bot.name), btcProfit!!)
        botProfitMetric.addMetric(listOf("USD", bot.id.toString(), bot.name), usdProfit!!)
    }

    override fun collect(): MutableList<MetricFamilySamples> {

        val botProfitMetric = GaugeMetricFamily(
            "threecommas_bots_profit_total",
            "3Commas bots profit by currency",
            listOf("currency", "bot_id", "bot_name"),
        )

        val metricFamilies = mutableListOf<MetricFamilySamples>()
        val bots = service.getBots()

        bots?.forEach { bot -> collectRealizedBotProfitMetric(bot, botProfitMetric) }

        metricFamilies.add(botProfitMetric)
        return metricFamilies
    }
}
