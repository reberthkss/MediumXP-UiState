package br.com.dialogystudios.todocreate.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.dialogystudios.extension.hideKeyboard
import br.com.dialogystudios.todocreate.databinding.TodoCreateFragmentBinding
import br.com.dialogystudios.todocreate.presentation.vm.TodoCreateState
import br.com.dialogystudios.todocreate.presentation.vm.TodoCreateViewModel
import kotlinx.android.synthetic.main.todo_create_fragment.view.normal
import kotlinx.android.synthetic.main.todo_create_normal_fragment.view.create_todo_btn
import kotlinx.android.synthetic.main.todo_create_normal_fragment.view.description_input
import kotlinx.android.synthetic.main.todo_create_normal_fragment.view.title_input

class TodoCreateFragment: Fragment() {
    private var _binding: TodoCreateFragmentBinding? = null
    private val binding: TodoCreateFragmentBinding
        get() = _binding!!
    private val vm: TodoCreateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoCreateFragmentBinding.inflate(layoutInflater)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        binding.viewFlipper.normal.title_input
            .doOnTextChanged { text, start, before, count ->
                with(vm) {
                    todo.value?.let {
                        updateTodo(it.copy(title = text.toString()))
                    }
                }
            }

        binding.viewFlipper.normal.description_input
            .doOnTextChanged { text, start, before, count ->
                with(vm) {
                    todo.value?.let {
                        updateTodo(it.copy(description = text.toString()))
                    }
                }
            }

        binding.viewFlipper.normal.create_todo_btn
            .setOnClickListener {
                requestSave()
                hideKeyboard()
            }
    }

    private fun setupSuccessView() {
        with(binding.success) {
            goBackBtn
                .setOnClickListener {
                    reset()
                }
            goToHome
                .setOnClickListener {
                    activity?.finish()
                }
        }
    }

    private fun setupLoadingView() {}

    private fun setupErrorView() {
        binding.error.retryBtn
            .setOnClickListener {
                requestSave()
            }

        binding.error.goBackBtn
            .setOnClickListener {
                reset()
            }
    }

    private fun reset() {
        vm.reset()
        resetView()
    }

    private fun resetView() {
        binding.normal.titleInput.text = null
        binding.normal.descriptionInput.text = null
    }

    private fun requestSave() {
        with(vm) {
            todo.value?.let {
                if (it.title.isEmpty()) {
                    Toast.makeText(context, "Title must be specified!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    vm.saveTodo(it.copy(timestamp = System.nanoTime()))
                }
            }
        }
    }

    private fun setupObservers() {
        vm.state.observe(viewLifecycleOwner, handleState)
    }

    private val handleState = Observer<TodoCreateState> {
        when (it) {
            is TodoCreateState.Normal -> handleNormal()
            is TodoCreateState.Success -> handleSuccess(it)
            is TodoCreateState.Loading -> handleLoading()
            is TodoCreateState.Error -> handleError(it)
        }
        binding.viewFlipper.displayedChild = it.ordinal
    }

    private fun handleNormal() {}

    private fun handleSuccess(state: TodoCreateState.Success) {}

    private fun handleLoading() {}

    private fun handleError(state: TodoCreateState.Error) {}


}