package br.com.dialogystudios.todocreate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dialogystudios.base.data.model.Todo
import br.com.dialogystudios.base.data.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoCreateViewModel: ViewModel() {
    private val repository = TodoRepository()
    private val disposable = CompositeDisposable()
    private val _state = MutableLiveData<TodoCreateState>(TodoCreateState.Normal)
    val state: LiveData<TodoCreateState>
        get() = _state
    private val _todo = MutableLiveData(Todo("", "", System.nanoTime()))
    val todo: LiveData<Todo>
        get() = _todo

    fun updateTodo(todo: Todo) {
        _todo.value = todo
    }

    fun saveTodo(todo: Todo) {
        repository
            .saveTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.postValue(TodoCreateState.Loading) }
            .doOnSuccess { _state.postValue(TodoCreateState.Success(it.id)) }
            .doOnError { _state.postValue(TodoCreateState.Error(it)) }
            .subscribe()
            .addTo(disposable)
    }

    fun reset() {
        _state.postValue(TodoCreateState.Normal)
        _todo.value = Todo("", "", System.nanoTime())
        disposable.clear()
    }
}

sealed class TodoCreateState(val ordinal: Int) {
    object Normal: TodoCreateState(0)
    data class Success(val todoId: String): TodoCreateState(1)
    object Loading: TodoCreateState(2)
    data class Error(val error: Throwable): TodoCreateState(3)
}