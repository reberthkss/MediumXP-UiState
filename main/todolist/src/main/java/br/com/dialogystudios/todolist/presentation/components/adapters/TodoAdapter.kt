package br.com.dialogystudios.todolist.presentation.components.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dialogystudios.base.data.model.TodoDocument
import br.com.dialogystudios.todolist.databinding.TodoViewHolderBinding
import br.com.dialogystudios.todolist.presentation.components.viewholders.TodoListener
import br.com.dialogystudios.todolist.presentation.components.viewholders.TodoViewHolder

class TodoAdapter (private val data: List<TodoDocument>, private val listener: TodoListener): RecyclerView.Adapter<TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoViewHolderBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TodoViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = data[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int = data.size
}