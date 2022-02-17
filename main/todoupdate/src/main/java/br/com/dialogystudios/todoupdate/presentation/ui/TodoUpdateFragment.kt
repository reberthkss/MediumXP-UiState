package br.com.dialogystudios.todoupdate.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.dialogystudios.base.data.model.Todo
import br.com.dialogystudios.todoupdate.databinding.TodoUpdateFragmentBinding
import br.com.dialogystudios.todoupdate.presentation.vm.TodoUpdateState
import br.com.dialogystudios.todoupdate.presentation.vm.TodoUpdateViewModel

class TodoUpdateFragment: Fragment() {
    private var _binding: TodoUpdateFragmentBinding? = null
    private val binding: TodoUpdateFragmentBinding
        get() = _binding!!
    private val vm: TodoUpdateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoUpdateFragmentBinding.inflate(layoutInflater)
        setup()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        vm.cancelRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        vm.loadTodo()
    }

    private fun setup() {
        setupView()
        setupObservers()
    }

    private fun setupView() {
        setupNormalView()
        setupSuccessView()
        setupLoadingView()
        setupErrorView()
    }

    private fun setupNormalView() {
        with (binding.normal) {
            todoTitleInput
                .doOnTextChanged { text, start, before, count ->
                    val newTodo = vm.todo.value?.copy(title = text.toString()) ?: Todo.newBlank()
                    setTodo(newTodo)
                }
            todoDescriptionInput
                .doOnTextChanged { text, start, before, count ->
                    val newTodo = vm.todo.value?.copy(description = text.toString()) ?: Todo.newBlank()
                    setTodo(newTodo)
                }
            saveBtn
                .setOnClickListener {
                    updateTodo()
                }
        }
    }

    private fun updateTodo() {
        vm.updateTodo()
    }

    private fun setTodo(newTodo: Todo) {
        vm.setTodo(newTodo)
    }

    private fun setupSuccessView() {
        with(binding.success) {
            editAgainBtn
                .setOnClickListener {
                    displayTodo()
                }
            toHomeBtn
                .setOnClickListener {
                    goToHome()
                }
        }
    }

    private fun displayTodo() {
        vm.showTodoData()
    }

    private fun goToHome() {
        Intent()
            .apply {
                putExtra("was_updated", true)
                activity?.setResult(Activity.RESULT_OK, this)
                activity?.finish()
            }
    }

    private fun setupLoadingView() {
        with (binding.loading) {}

    }

    private fun setupErrorView() {
        with (binding.error) {
            retryBtn
                .setOnClickListener {
                    updateTodo()
                }
            goToHomeBtn
                .setOnClickListener {
                    goToHome()
                }
        }

    }

    private fun setupObservers() {
        vm.state.observe(viewLifecycleOwner, handleState)
    }

    private val handleState = Observer<TodoUpdateState> { newState ->
        when (newState) {
            is TodoUpdateState.Normal -> handleNormalState()
            is TodoUpdateState.Success -> handleSuccessState(newState)
            is TodoUpdateState.Loading -> handleLoadingState()
            is TodoUpdateState.Error -> handleErrorState()
        }
        binding.viewFlipper.displayedChild = newState.ordinal
    }

    private fun handleNormalState() {}

    private fun handleSuccessState(successState: TodoUpdateState.Success) {
        when (successState) {
            is TodoUpdateState.Success.LoadTodo -> handleLoadTodoState(successState)
            is TodoUpdateState.Success.UpdateTodo -> handleUpdateTodoState(successState)
        }
    }

    private fun handleLoadingState() {}

    private fun handleErrorState() {}

    private fun handleLoadTodoState(successState: TodoUpdateState.Success.LoadTodo) {
        with (binding.normal) {
            todoTitleInput.setText(successState.todo.title)
            todoDescriptionInput.setText(successState.todo.description)
        }
        displayTodo()
    }

    private fun handleUpdateTodoState(successState: TodoUpdateState.Success.UpdateTodo) {

    }

}