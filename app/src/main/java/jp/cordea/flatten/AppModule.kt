package jp.cordea.flatten

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.logging.*
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient {
            install(Logging)
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(BuildConfig.FLAT_TOKEN, "")
                    }
                }
            }
            defaultRequest {
                url("https://api.flat.io/v2")
            }
        }
    }
}
