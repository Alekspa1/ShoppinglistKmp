package com.drag0n.shoppinglistkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drag0n.shoppinglistkmp.data.settings.MySettings
import com.drag0n.shoppinglistkmp.domain.useCases.AddItemShopListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.CreateShopListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.DeleteUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetAllShopsListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetItemsListUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.GetKeyUseCase
import com.drag0n.shoppinglistkmp.domain.useCases.UpdateItemUseCase
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
    private val additemShopList: AddItemShopListUseCase,
    private val delete: DeleteUseCase,
    private val update: UpdateItemUseCase,
) : ViewModel() {


    val keyFlow = MutableStateFlow(settings.getKey())
    val errorFlow = MutableSharedFlow<String>()

    private val _expandedId = MutableStateFlow<Int?>(null)
    val expandedId = _expandedId.asStateFlow()
    private val refreshSignal = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    init {
        viewModelScope.launch {
            key().onSuccess { key ->
                initKey(key)
            }
                .onFailure { error ->
                    errorFlow.emit("Произошла ошибка получения ключа")

                }
        }
    }


    fun createShopList(name: String) {
        viewModelScope.launch {
            createList(keyFlow.value, name)
                .onSuccess { success ->
                    if (success.success) initKey(keyFlow.value)

                }
                .onFailure { error ->
                    errorFlow.emit("Произошла ошибка создания списка")
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val shopListFlow = keyFlow.flatMapLatest { key ->
        flow {
            if (key.isEmpty()) {
                return@flow
            }
            while (true) {
                getAllShopList(key).onSuccess {
                    emit(it.shopList)
                }.onFailure { error ->
                    errorFlow.emit("Произошла ошибка, возможно вы ошиблись с ключем списка или неполадки с сетью")
                    emit(emptyList())
                    return@flow
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
    val itemsListFlow = combine(expandedId, refreshSignal) { id, _ -> id }.flatMapLatest { id ->
        flow {
            if (id == null || id == 0) {
                emit(emptyList())
                return@flow
            }
            while (true) {
                getItemsList(id).onSuccess {
                    emit(it.itemList)
                }.onFailure { error ->
                    errorFlow.emit("Произошла ошибка получения элементов списка")
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

    fun addItemsShopListFun(value: String, count: Int) {
        viewModelScope.launch {
            val id = expandedId.value ?: 0
            additemShopList(id, value, count)

        }
    }

    fun deleteFun(id: Int, who: String) {
        viewModelScope.launch {
            delete(id, who)
        }
    }

    fun updateFun(id: Int,value: String,n: String,who: String){
        viewModelScope.launch {
            update(id, value,n,who)
        }
    }
}