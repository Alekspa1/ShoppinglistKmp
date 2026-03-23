package com.drag0n.shoppinglistkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drag0n.shoppinglistkmp.domain.useCases.GetKeyUseCase
import kotlinx.coroutines.launch

class MyViewModel(
    private val key: GetKeyUseCase) : ViewModel() {




    init {

    viewModelScope.launch {
        key().onSuccess {key->
            println("Успешно")
        }
            .onFailure { error->
                println(error.message.toString())
            }
    }
    }
}