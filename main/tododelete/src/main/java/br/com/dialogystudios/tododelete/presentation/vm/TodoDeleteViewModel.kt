package br.com.dialogystudios.tododelete.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dialogystudios.base.data.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoDeleteViewModel: ViewModel() {
    private val _state = MutableLiveData<TodoDeleteState>(TodoDeleteState.Normal)
    val state: LiveData<TodoDeleteState>
        get() = _state
    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id
    private val repository: TodoRepository by lazy {
        TodoRepository()
    }
    private val disposable = CompositeDisposable()

    fun delete() {
        id.value?.let {
            repository
                .delete(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _state.postValue(TodoDeleteState.Loading) }
                .doOnComplete { _state.postValue(TodoDeleteState.Success) }
                .doOnError { _state.postValue(TodoDeleteState.Error(it)) }
                .subscribe()
                .addTo(disposable)
        }
    }

    fun setTodoID(id: String) {
        _id.value = id
    }
}

sealed class TodoDeleteState(val ordinal: Int) {
    object Normal: TodoDeleteState(0)
    object Success: TodoDeleteState(1)
    object Loading: TodoDeleteState(2)
    data class Error(val error: Throwable): TodoDeleteState(3)
}