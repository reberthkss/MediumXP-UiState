package br.com.dialogystudios.todolist.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dialogystudios.base.data.model.TodoDocument
import br.com.dialogystudios.base.data.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoListViewModel: ViewModel() {
    private val disposable = CompositeDisposable()
    private val _state = MutableLiveData<TodoListState>(TodoListState.Loading)
    val state: LiveData<TodoListState>
        get() = _state

    private val repository by lazy {
        TodoRepository()
    }

    fun getTodoList() {
        repository
            .getTodoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.postValue(TodoListState.Loading) }
            .doOnSuccess { _state.postValue(TodoListState.Success(it)) }
            .doOnError { _state.postValue(TodoListState.Error(it)) }
            .subscribe()
            .addTo(disposable)
    }
}

sealed class TodoListState(val ordinal: Int) {
    data class Success(val todoList: List<TodoDocument>): TodoListState(0)
    data class Error(val error: Throwable): TodoListState(1)
    object Loading: TodoListState(2)
}