package data.source.remote

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utils.Constant.Companion.BASE_URL

abstract class Api {

    val client = httpClient

    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
        }
    }
}

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(jsonConfiguration)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}

private val jsonConfiguration
    get() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }





