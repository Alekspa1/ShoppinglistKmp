package com.drag0n.shoppinglistkmp.data.network

import com.drag0n.shoppinglistkmp.domain.model.KeyToken
import com.drag0n.shoppinglistkmp.domain.repository.KeyRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

class KeyRepositoryImpl(private val ktor: HttpClient) : KeyRepository {
    override suspend fun createKey(): Result<KeyToken> = runCatching {
        ktor.post("https://cyber-unisafe.ru/shopping/v4/Registration").body()
    }
}