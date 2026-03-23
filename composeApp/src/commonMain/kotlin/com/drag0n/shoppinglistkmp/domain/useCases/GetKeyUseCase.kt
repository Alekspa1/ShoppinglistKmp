package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.data.settings.MySettings
import com.drag0n.shoppinglistkmp.domain.model.KeyToken
import com.drag0n.shoppinglistkmp.domain.repository.KeyRepository

class GetKeyUseCase(private val repository : KeyRepository, private val settings : MySettings) {

    suspend operator fun invoke(): Result<String> {
        val currentKey = settings.getKey()

        return if (currentKey.isEmpty()) {
            repository.createKey().map { response ->
                val newKey = response.key
                settings.saveKey(newKey)
                println("Ключа нет, вот он: $newKey")
                newKey
            }
        } else {
            println("Ключ уже есть, вот он: $currentKey")
            Result.success(currentKey)
        }
    }


}