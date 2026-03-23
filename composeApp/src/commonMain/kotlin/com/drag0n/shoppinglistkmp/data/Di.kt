package com.drag0n.shoppinglistkmp.data

import com.drag0n.shoppinglistkmp.MyViewModel
import com.drag0n.shoppinglistkmp.data.network.KeyRepositoryImpl
import com.drag0n.shoppinglistkmp.domain.repository.KeyRepository
import com.drag0n.shoppinglistkmp.domain.useCases.GetKeyUseCase
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.ktor.client.plugins.logging.Logger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration

val appModule = module {

    single<HttpClient> {

        HttpClient {
            install(Logging) {

                logger = object : Logger {
                    override fun log(message: String) {
                        println("Logger in DiCommon: ${message}")
                    }
                }
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<Settings> { Settings() }
    single<KeyRepository> { KeyRepositoryImpl(get()) }
    viewModelOf(::MyViewModel)

    factory<GetKeyUseCase> { GetKeyUseCase(get(),get()) }

}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }
fun initKoinIos() = initKoin {}