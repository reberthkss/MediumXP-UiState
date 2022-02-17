package br.com.dialogystudios.tododetail.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dialogystudios.base.data.model.Todo
import br.com.dialogystudios.base.data.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoDetailViewModel: ViewModel() {
    private val _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id
    private val repository: TodoRepository by lazy {
        TodoRepository()
    }
    private val _state = MutableLiveData<TodoDetailState>()
    val state: LiveData<TodoDetailState>
        get() = _state
    private val disposable = CompositeDisposable()

    fun setId(id: String) {
        _id.value = id
    }

    fun loadTodo() {
        id.value?.let {
            repository
                .getTodoByID(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _state.postValue(TodoDetailState.Loading) }
                .subscribeBy(
                    {
                        _state.postValue(TodoDetailState.Error(it))
                    },
                    {
                        _state.postValue(TodoDetailState.Success(it))
                    }
                )
                .addTo(disposable)
        }

    }

}

sealed class TodoDetailState(val ordinal: Int) {
    data class Success(val todo: Todo): TodoDetailState(0)
    object Loading: TodoDetailState(1)
    data class Error(val error: Throwable): TodoDetailState(2)
}