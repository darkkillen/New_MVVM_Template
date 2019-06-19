package com.darkkillen.archmvvm.api

import android.content.Context
import com.darkkillen.archmvvm.BuildConfig
import com.darkkillen.archmvvm.utils.LiveNetworkMonitor
import com.darkkillen.archmvvm.utils.NetworkMonitor
import com.darkkillen.archmvvm.utils.NoNetworkException
import com.darkkillen.archmvvm.vo.Config
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


object ServiceGenerator {

    /**
     * Create Service Request to Server
     *
     * @param clazz
     * @param <T>
     * @return </T>
     * */
    fun <T> create(context: Context, networkMonitor: LiveNetworkMonitor, config: Config, clazz: Class<T>): T {

        val cacheDir = File(context.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)


        val loggerInterceptor = Interceptor { chain ->
            val request = chain.request()

            // Logging, active only in debug mode
            val t1 = System.nanoTime()
            Timber.d(String.format(
                    "Sending request %s on %s%n%s parameters: %s",
                    request.url(), chain.connection(), request.headers(), stringifyRequestBody(request)
                )
            )

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Timber.d(
                String.format(
                    "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.headers()
                )
            )

            val responseString = String(response.body()!!.bytes())

            Timber.d("Response: $responseString")

            response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), responseString))
                .build()
        }




        // Intercept Parameters
        httpClient.addInterceptor { chain ->
            var request = chain.request()
            request = interceptParameter(request)
            chain.proceed(request)
        }

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder().apply {
//                addHeader("Accept-Language", config.languageKey)
            }

            val request = requestBuilder.build()
            try {
//                val auth = request.header("Authorization")
//                val lang = request.header("Accept-Language")
            } catch (e: Exception) {
                Timber.e("Message ---> %s", e.message)
            }

            chain.proceed(request)
        }

        // Network monitor interceptor:
        httpClient.addInterceptor { chain ->
            if (networkMonitor.isConnected()) {
                return@addInterceptor chain.proceed(chain.request())
            } else {
                throw NoNetworkException()
            }
        }

        // Add http logger
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(loggerInterceptor)
        }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(config.httpUrl)
                .client(client)
                .build()

        return retrofit.create(clazz)
    }

    private fun interceptParameter(request: Request): Request {
        val url = request.url().newBuilder().addQueryParameter("clientType", "androidMobile").build()
        return request.newBuilder().url(url).build()
    }

    /**
     * GET Value Request
     *
     * @param request
     * @return
     */
    private fun toString(request: Request): String? {
        try {
            if (request.method() == "GET") {
                return request.url().query()
            } else {
                val copy = request.newBuilder().build()
                if (copy != null) {
                    val buffer = Buffer()
                    val body = copy.body()
                    if (body != null) {
                        body.writeTo(buffer)
                        return buffer.readUtf8()
                    }
                }
            }
            return "null"
        } catch (e: IOException) {
            return "did not work"
        }

    }

    private fun stringifyRequestBody(request: Request): String {
        if (request.body() != null) {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body()!!.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                Timber.w( "Failed to stringify request body: %s", e.message)
            } catch (e: NullPointerException) {
                Timber.w( "Failed to stringify request body: %s", e.message)
            }

        }
        return ""
    }

}