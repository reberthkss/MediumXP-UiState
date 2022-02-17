package br.com.dialogystudios.base.data.repository

import br.com.dialogystudios.base.data.model.Todo
import br.com.dialogystudios.base.data.model.TodoDocument
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class TodoRepository {
    private val db = Firebase.firestore

    private val collection = db
        .collection(TODO_COLLECTION)

    fun saveTodo(todo: Todo): Single<DocumentReference> {
        return Single.create { emitter ->
            collection
                .add(todo)
                .addOnSuccessListener {
                    emitter.onSuccess(it)
                }
                .addOnCanceledListener {
                    emitter.onError(Throwable("canceled"))
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun getTodoByID(todoID: String): Single<Todo> {
        return Single.create { emitter ->
            collection
                .document(todoID)
                .get()
                .addOnSuccessListener {
                    if (it.data.isNullOrEmpty()) {
                        emitter
                            .onError(TodoRepositoryError.NotFound)
                    } else {
                        emitter
                            .onSuccess(
                                Todo
                                    .from(
                                        it.data!!
                                    )
                            )
                    }

                }
                .addOnCanceledListener {
                    emitter.onError(TodoRepositoryError.Canceled)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun getTodoList(): Single<List<TodoDocument>> {
        return Single.create { emitter ->
            collection
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {
                    it.documents
                        .map {
                            TodoDocument(
                                id= it.id,
                                todo= Todo.from(it.data as Map<String, Any>)
                            )
                        }
                        .apply {
                            emitter.onSuccess(this)
                        }
                }
                .addOnCanceledListener {
                    emitter.onError(TodoRepositoryError.Canceled)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun delete(todoID: String): Completable {
        return Completable.create { emitter ->
            collection
                .document(todoID)
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnCanceledListener {
                    emitter.onError(TodoRepositoryError.Canceled)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun update(todoID: String, newData: Todo): Completable {
        return Completable.create { emitter ->
            collection
                .document(todoID)
                .update(newData.toMap())
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnCanceledListener {
                    emitter.onError(TodoRepositoryError.Canceled)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    companion object {
        private const val TODO_COLLECTION = "todo"
    }
}


sealed class TodoRepositoryError: Throwable() {
    object NotFound: TodoRepositoryError()
    object Canceled: TodoRepositoryError()
}