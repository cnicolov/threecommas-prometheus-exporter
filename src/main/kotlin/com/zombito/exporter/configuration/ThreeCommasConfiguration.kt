package com.zombito.exporter.configuration

import com.fasterxml.jackson.databind.json.JsonMapper
import com.zombito.exporter.services.ThreeCommasApi
import com.zombito.exporter.services.ThreeCommasAuthenticator
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
@ConfigurationProperties(prefix = "3commas")
class ServiceConfiguration {
  lateinit var apiKey: String
  lateinit var apiSecret: String
  lateinit var baseUrl: String

  @Bean
  fun threeCommasApiService() : ThreeCommasApi {
    val client = ThreeCommasAuthenticator(apiKey, apiSecret).createAuthenticatedClient()
    val mapper = JsonMapper.builder().findAndAddModules().build()
    val retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(JacksonConverterFactory.create(mapper))
      .build()

    return retrofit.create(ThreeCommasApi::class.java)
  }
}
