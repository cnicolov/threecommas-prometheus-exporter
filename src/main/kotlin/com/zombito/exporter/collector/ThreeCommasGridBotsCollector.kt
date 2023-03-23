package com.zombito.exporter.collector

import com.zombito.exporter.model.GridBot
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily

class ThreeCommasGridBotsCollector(private val service: ThreeCommasService) : Collector() {

  private val labelKeys = listOf<String>("id", "name", "pair", "currency")
  private val THREECOMMAS_METRICS_PREFIX = "threecommas_grid_bot"
  private fun metricName(name : String) = "${THREECOMMAS_METRICS_PREFIX}_${name}"

  override fun collect() : MutableList<MetricFamilySamples> {

    val metrics =  mutableListOf<MetricFamilySamples>()

    val currentBotProfitMetric = GaugeMetricFamily(
      metricName("current_profit"),
      "Metric to show current profit of a grid bot",
      labelKeys
    )

    val unrealizedBotProfitMetric = GaugeMetricFamily(
      metricName("unrealized_profit"),
      "Metric to show grid bot unrealized profit.",
      labelKeys - listOf("currency")
    )

    val bots = service.getGridBots()
    bots.forEach { gridBot ->
      collectGridBotProfit(gridBot, currentBotProfitMetric)
      collectUnrealizedProfit(gridBot, unrealizedBotProfitMetric)
    }

    metrics.add(currentBotProfitMetric)
    metrics.add(unrealizedBotProfitMetric)

    return metrics
  }

  private fun collectUnrealizedProfit(gridBot: GridBot, metric: GaugeMetricFamily) {
    val labels = mutableListOf(gridBot.id.toString(), gridBot.name, gridBot.pair)
    metric.addMetric(labels, gridBot.unrealizedPnl)
  }

  private fun collectGridBotProfit(gridBot: GridBot, metric: GaugeMetricFamily) {
    val labels = mutableListOf(gridBot.id.toString(), gridBot.name, gridBot.pair, "USD")
    metric.addMetric(labels, gridBot.currentProfitUsd)
  }
}