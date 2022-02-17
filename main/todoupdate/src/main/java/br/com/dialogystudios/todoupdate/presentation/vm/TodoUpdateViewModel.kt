package br.com.dialogystudios.todoupdate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dialogystudios.base.data.model.Todo
import br.com.dialogystudios.base.data.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoUpdateViewModel: ViewModel() {
    private val _state = MutableLiveData<TodoUpdateState>(TodoUpdateState.Loading)
    val state: LiveData<TodoUpdateState>
        get() = _state

    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo>
        get() = _todo

    private val disposable = CompositeDisposable()

    private val repository: TodoRepository by lazy {
        TodoRepository()
    }

    fun setID(id: String) {
        _id.value = id
    }

    fun loadTodo() {
        id.value?.let {
            repository
                .getTodoByID(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _state.value = TodoUpdateState.Loading }
                .doOnSuccess { _state.value = TodoUpdateState.Success.LoadTodo(it) }
                .doOnError { _state.value = TodoUpdateState.Error(it) }
                .subscribe()
                .addTo(disposable)
        }
    }

    fun updateTodo() {
        todo.value?.let { updatedTodo ->
            id.value?.let { todoID ->
                repository
                    .update(todoID, updatedTodo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _state.value = TodoUpdateState.Loading }
                    .doOnComplete { _state.value = TodoUpdateState.Success.UpdateTodo }
                    .doOnError { _state.value = TodoUpdateState.Error(it) }
                    .subscribe()
                    .addTo(disposable)
            }
        }
    }

    fun setTodo(todo: Todo) {
        _todo.value = todo
    }

    fun showTodoData() {
        _state.value = TodoUpdateState.Normal
    }

}

sealed class TodoUpdateState(val ordinal: Int) {
    object Normal: TodoUpdateState(0)
    sealed class Success: TodoUpdateState(1) {
        data class LoadTodo(val todo: Todo): TodoUpdateState.Success()
        object UpdateTodo: TodoUpdateState.Success()
    }

    object Loading: TodoUpdateState(2)

    data class Error(val error: Throwable): TodoUpdateState(3)
}