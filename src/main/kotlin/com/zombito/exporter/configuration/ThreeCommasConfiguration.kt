package com.zombito.exporter.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.squareup.moshi.Moshi
import com.zombito.exporter.services.ThreeCommasApi
import com.zombito.exporter.services.ThreeCommasAuthenticator
import com.zombito.exporter.services.ThreeCommasService
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Component
@ConfigurationProperties(prefix = "3commas")
class ThreeCommasConfiguration {
  lateinit var apiKey: String
  lateinit var apiSecret: String
  lateinit var baseUrl: String

  @Bean
  fun threeCommasApiService() : ThreeCommasApi {
    // TODO: Encapsulate this in service package
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
