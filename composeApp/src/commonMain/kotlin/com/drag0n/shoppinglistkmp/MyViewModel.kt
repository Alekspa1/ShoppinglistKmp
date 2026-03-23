package com.drag0n.shoppinglistkmp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drag0n.shoppinglistkmp.data.settings.MySettings
import com.drag0n.shoppinglistkmp.domain.model.Shop
import com.drag0n.shoppinglistkmp.domain.useCases.AddItemShopListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.CreateShopListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetAllShopsListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetItemsListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetKeyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyViewModel(
    private val key: GetKeyUseCase,
    private val createList: CreateShopListUseCase,
    private val settings: MySettings,
    private val getAllShopList: GetAllShopsListUseCase,
    private val getItemsList: GetItemsListUseCase,
    private val additemShopList: AddItemShopListUseCase
) : ViewModel() {



    private val keyFlow = MutableStateFlow(settings.getKey())

    private val _expandedId = MutableStateFlow<Int?>(null)
    val expandedId = _expandedId.asStateFlow()
    private val refreshSignal = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }
    init {
    viewModelScope.launch {
        key().onSuccess {key->
            initKey(key)
            println("Key: $key")
        }
            .onFailure { error->
                println(error.message.toString())
            }
    }
    }


    fun createShopList(name: String){
        viewModelScope.launch {
            createList(settings.getKey(),name)
                .onSuccess {success ->
                  if (success.success) initKey(settings.getKey())

                }
                .onFailure {
                    error->

                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val shopListFlow = keyFlow.flatMapLatest { key->
        flow {
            if (key.isEmpty()) {
                return@flow
            }
            while (true){
            getAllShopList(key).onSuccess {
                emit(it.shopList)
            }.onFailure { error->
                println("Произошла ошибка ${error.message.toString()}")
            }
                delay(10000)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val itemsListFlow = combine(expandedId, refreshSignal){id,_ -> id} .flatMapLatest { id->
        flow {
            if (id == null || id == 0) {
                emit(emptyList())
                return@flow
            }
            while (true){
                getItemsList(id).onSuccess {
                    emit(it.itemList)
                }.onFailure { error->
                    println("Произошла ошибка ${error.message.toString()}")
                }
                delay(5000)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun initKey(key: String) {
        keyFlow.value = ""
        keyFlow.value = key
    }
    fun myKey() = settings.getKey()

    fun toggleExpand(id: Int?) {
        _expandedId.value = if (_expandedId.value == id) null else id
    }

    fun addItemsShopListFun(value: String, count: Int){
        viewModelScope.launch {
            val id = expandedId.value ?: 0
            additemShopList(id,value,count)

        }
    }
    fun refreshProducts() {
        refreshSignal.tryEmit(Unit)
    }
}