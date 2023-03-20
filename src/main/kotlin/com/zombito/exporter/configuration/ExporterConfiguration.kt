package com.zombito.exporter.configuration

import com.zombito.exporter.ThreeCommasBotsPrometheusCollector
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.CollectorRegistry

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
    fun threeCommasCollector() : ThreeCommasBotsPrometheusCollector {
        return ThreeCommasBotsPrometheusCollector(threeCommasService).register(collectorRegistry())
    }
}