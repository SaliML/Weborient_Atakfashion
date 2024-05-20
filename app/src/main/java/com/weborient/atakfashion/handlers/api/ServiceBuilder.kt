package com.weborient.atakfashion.handlers.api

import com.weborient.atakfashion.config.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object ServiceBuilder {
    fun createServiceWithBearer(){
        AppConfig.apiServiceWithBearer = buildService(IApiRequests::class.java, true)
    }

    fun createServiceWithoutBearer(){
        if(AppConfig.apiServiceWithoutBearer == null){
            AppConfig.apiServiceWithoutBearer = buildService(IApiRequests::class.java, false)
        }
    }

    fun<T> buildService(service: Class<T>, needToken: Boolean): T?{
        try{
            if(needToken){

                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                val sslSocketFactory = sslContext.socketFactory

                val client = OkHttpClient.Builder()
                    .callTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor{chain ->
                        val request = chain
                            .request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer ${AppConfig.accessToken}")
                            .build()
                        chain.proceed((request))
                    }.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager).hostnameVerifier{ _, _ -> true }.build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(AppConfig.apiAddress)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(service)
            }
            else{
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                val sslSocketFactory = sslContext.socketFactory

                val client = OkHttpClient.Builder()
                    .callTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager).hostnameVerifier{ _, _ -> true }.build()


                val retrofit = Retrofit.Builder()
                    .baseUrl(AppConfig.apiAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                return retrofit.create(service)
            }
        }
        catch(_: Exception){
            return null
        }
    }
}