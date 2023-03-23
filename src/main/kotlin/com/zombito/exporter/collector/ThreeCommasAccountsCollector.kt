package com.zombito.exporter.collector

import com.zombito.exporter.model.Account
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily
import org.springframework.stereotype.Component

@Component
class ThreeCommasAccountsCollector(private val service: ThreeCommasService) : Collector() {
  override fun collect(): MutableList<MetricFamilySamples> {
    val accountBalanceMetric = GaugeMetricFamily(
      "threecommas_account_balance",
      "How much moneyz we haz in threecommas",
      listOf("account_id", "currency")
    )

    service.getAccounts().forEach { account -> collectAccountBalance(account, accountBalanceMetric) }
    return mutableListOf(accountBalanceMetric)
  }

  private fun collectAccountBalance(account: Account, metric: GaugeMetricFamily) {
    metric.addMetric(listOf(account.id.toString(), "USD"), account.usdAmount)
    metric.addMetric(listOf(account.id.toString(), "BTC"), account.btcAmount)
  }
}