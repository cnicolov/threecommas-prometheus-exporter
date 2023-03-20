package com.zombito.exporter.metrics

import io.prometheus.client.Collector.MetricFamilySamples

interface Metric {
  fun collect() : MutableList<MetricFamilySamples>
}