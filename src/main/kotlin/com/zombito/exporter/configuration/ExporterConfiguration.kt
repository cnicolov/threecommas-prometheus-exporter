package com.zombito.exporter.configuration

import com.zombito.exporter.ThreeCommasPrometheusCollector
import com.zombito.exporter.services.ThreeCommasService
import io.prometheus.client.CollectorRegistry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ExporterConfiguration @Autowired constructor (val threeCommasService: ThreeCommasService) {

    @Bean
    fun collectorRegistry() : CollectorRegistry {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    fun threeCommasCollector(service: ThreeCommasService) : ThreeCommasPrometheusCollector {
        return ThreeCommasPrometheusCollector(service).register(collectorRegistry())
    }
}
