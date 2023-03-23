package com.zombito.exporter.configuration

import com.zombito.exporter.collector.ThreeCommasAccountsCollector
import com.zombito.exporter.collector.ThreeCommasBotsCollector
import com.zombito.exporter.collector.ThreeCommasGridBotsCollector
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.Collector
import io.prometheus.client.Collector.MetricFamilySamples
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.CounterMetricFamily
import org.springframework.boot.context.properties.ConfigurationProperties

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExporterConfiguration constructor(val threeCommasService: ThreeCommasService) {


    /**
     * @author: Kristiyan Nikolov
     * Export as bean for Spring collectors to use
     */
    @Bean
    fun collectorRegistry() : CollectorRegistry {
        return CollectorRegistry.defaultRegistry
    }

    @Bean
    fun botsCollector() : ThreeCommasBotsCollector {
        return ThreeCommasBotsCollector(threeCommasService).register(collectorRegistry())
    }

    @Bean
    fun accountsCollector() : ThreeCommasAccountsCollector {
        return ThreeCommasAccountsCollector(threeCommasService).register(collectorRegistry())
    }

    @Bean
    fun threeCommasGridBotsCollector() : ThreeCommasGridBotsCollector {
        return ThreeCommasGridBotsCollector(threeCommasService).register(collectorRegistry())
    }
}