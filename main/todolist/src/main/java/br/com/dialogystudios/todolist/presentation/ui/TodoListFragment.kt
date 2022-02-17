package br.com.dialogystudios.todolist.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.dialogystudios.todolist.databinding.TodoListFragmentBinding
import br.com.dialogystudios.todolist.presentation.components.adapters.TodoAdapter
import br.com.dialogystudios.todolist.presentation.components.viewholders.TodoListener
import br.com.dialogystudios.todolist.presentation.vm.TodoListState
import br.com.dialogystudios.todolist.presentation.vm.TodoListViewModel

class TodoListFragment: Fragment() {
    private var _binding: TodoListFragmentBinding? = null
    private val binding: TodoListFragmentBinding
        get() = _binding!!
    private val vm : TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoListFragmentBinding.inflate(layoutInflater, container, false)
        setupOnCreate()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupOnStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupOnCreate () {
        setupView()
        setupObserver()
    }

    private fun setupOnStart() {
        vm.getTodoList()
    }


    private fun setupView() {
        setupSuccessView()
    }

    private fun setupSuccessView() {
        binding.success.createTodoBtn
            .setOnClickListener {
                Intent()
                    .apply {
                        data = Uri.parse("mediumxp://create-todo/")
                        startActivity(this)
                    }
            }
    }

    private fun setupObserver() {
        vm.state.observe(viewLifecycleOwner, handleState)
    }

    private val handleState = Observer<TodoListState> { newState ->
        when (newState) {
            is TodoListState.Error -> handleError(newState)
            is TodoListState.Loading -> handleLoading()
            is TodoListState.Success -> handleSuccess(newState)
        }
        binding.viewFlipper.displayedChild = newState.ordinal
    }

    private fun handleError(error: TodoListState.Error) {
        Toast
            .makeText(context, "Error => ${error.error.localizedMessage}", Toast.LENGTH_LONG)
            .show()
    }

    private fun handleLoading() {}

    private fun handleSuccess(success: TodoListState.Success) {
        binding.success.todoRv.adapter = TodoAdapter(success.todoList, todoViewHolderListener)
    }

    private val todoViewHolderListener  = object : TodoListener {
        override fun onContainerClicked(id: String) {
            Intent(Intent.ACTION_VIEW)
                .apply {
                    data = Uri.parse("mediumxp://todo-detail?id=${id}")
                    startActivity(this)
                }
        }

        override fun onUpdateClicked(id: String) {
            Intent(Intent.ACTION_VIEW)
                .apply {
                    data = Uri.parse("mediumxp://todo-update?id=${id}")
                    startActivity(this)
                }
        }

        override fun onDeleteClicked(id: String) {
            Intent(Intent.ACTION_VIEW)
                .apply {
                    data = Uri.parse("mediumxp://todo-delete?id=${id}")
                    startActivity(this)
                }
        }
    }
}