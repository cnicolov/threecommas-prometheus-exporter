package com.zombito.exporter

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ExporterApplication

fun main(args: Array<String>) {
	runApplication<ExporterApplication>(*args)
}