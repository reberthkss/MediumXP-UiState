package br.com.dialogystudios.todolist.presentation.components.viewholders

import androidx.recyclerview.widget.RecyclerView
import br.com.dialogystudios.base.data.model.TodoDocument
import br.com.dialogystudios.todolist.databinding.TodoViewHolderBinding

interface TodoListener {
    fun onContainerClicked (id: String)
    fun onUpdateClicked(id: String)
    fun onDeleteClicked(id: String)
}

class TodoViewHolder(val binding: TodoViewHolderBinding, private val listener: TodoListener): RecyclerView.ViewHolder(binding.root) {
    private lateinit var data: TodoDocument
    fun bind(document: TodoDocument) {
        data = document
        setup()
    }

    private fun setup() {
        setupView()
        setupListeners()
    }

    private fun setupView() {
        binding.todoTitleTxt.text = data.todo.title
    }

    private fun setupListeners() {
        with (listener) {
            binding.card
                .setOnClickListener {
                    onContainerClicked(data.id)
                }
            binding.deleteBtn
                .setOnClickListener {
                    onDeleteClicked(data.id)
                }
            binding.updateBtn
                .setOnClickListener {
                    onUpdateClicked(data.id)
                }
        }
    }


}