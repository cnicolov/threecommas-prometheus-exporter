package com.zombito.exporter.metrics

import com.zombito.exporter.model.Account
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector.MetricFamilySamples
import io.prometheus.client.GaugeMetricFamily
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class AccountBalanceMetric(name : String, help : String, labels: SortedMap<String,String>) : Metric {

  @Autowired
  private lateinit var threeCommas: ThreeCommasService

  private val metric: GaugeMetricFamily = GaugeMetricFamily(name, help, labels.keys.toList())

  override fun collect() : MutableList<MetricFamilySamples> {
    threeCommas.getAccounts().forEach { account: Account ->
      metric.addMetric(listOf(account.id.toString(), "USD"), account.usdAmount)
      metric.addMetric(listOf(account.id.toString(), "BTC"), account.btcAmount)
    }
    return metric
  }
}