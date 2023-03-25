package com.zombito.exporter.collector

import com.zombito.exporter.model.Deal
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily
import java.time.OffsetDateTime

class ThreeCommasDealsCollector(private val service: ThreeCommasService) : Collector() {

  override fun collect(): MutableList<MetricFamilySamples> {

    val dealDurationMetric = GaugeMetricFamily(
      "threecommas_open_deals_duration_hours",
      "Histogram with deal durations for analysis.",
      listOf("bot_id", "pair")
    )

    val metrics = mutableListOf<MetricFamilySamples>(dealDurationMetric)

    service.getDeals().forEach { deal: Deal ->

      if (!deal.finished) {
        val p = OffsetDateTime.now().toEpochSecond() - deal.createdAt.toEpochSecond()
        dealDurationMetric.addMetric(
          listOf(deal.botId.toString(), deal.pair),
          p.toDouble() / 3600
        )
      }
    }

    return metrics
  }
}