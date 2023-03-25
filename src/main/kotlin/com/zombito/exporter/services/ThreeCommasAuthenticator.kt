package com.zombito.exporter.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author: Kristiyan Nikolov
 */
class ThreeCommasAuthenticator( private val apiKey: String, private val apiSecret: String) {

  private fun byteToHex(byteArray: ByteArray) : String {
    val sb = StringBuilder(byteArray.size* 2)
    for (b in byteArray) {
      sb.append(String.format("%02x", b))
    }
    return sb.toString()
  }

  private fun generateSignature(request: Request) : String {

    val path = request.url.toUrl().path
    val query = request.url.toUrl().query
    var message = path

    if (!query.isNullOrEmpty()) {
      message = "${path}?${query}"
    }

    val secret = apiSecret.toByteArray()
    val secretKeySpec = SecretKeySpec(secret, "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(secretKeySpec)

    val output = mac.doFinal(message.toByteArray(Charsets.UTF_8))
    return byteToHex(output)
  }

  private fun createRequest(requestBuilder: Request.Builder) : Request {
    val request = requestBuilder.build()
    val signature = generateSignature(request)

    return request.newBuilder()
      .header("Apikey", apiKey)
      .header("Signature", signature)
      .build()
  }

  fun createAuthenticatedClient() : OkHttpClient {
    val interceptor = Interceptor { chain ->
      val originalRequest = chain.request()
      val authenticatedRequest = createRequest(originalRequest.newBuilder())
      chain.proceed(authenticatedRequest)
    }

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .addInterceptor(loggingInterceptor)
      .build()
  }
}