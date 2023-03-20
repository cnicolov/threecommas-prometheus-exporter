package com.zombito.exporter

import com.zombito.exporter.services.ThreeCommasService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExporterApplicationTests {

	@Autowired
	lateinit var service: ThreeCommasService

	@Test
	fun contextLoads() {
	}

	@Test
	fun getAccounts() {
		val accounts = service.getAccounts()
		assertThat(accounts.isNotEmpty())
	}
}
