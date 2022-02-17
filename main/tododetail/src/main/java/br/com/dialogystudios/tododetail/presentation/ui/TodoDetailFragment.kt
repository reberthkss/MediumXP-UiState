package br.com.dialogystudios.tododetail.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.dialogystudios.tododetail.databinding.TodoDetailFragment2Binding
import br.com.dialogystudios.tododetail.presentation.vm.TodoDetailState
import br.com.dialogystudios.tododetail.presentation.vm.TodoDetailViewModel

class TodoDetailFragment: Fragment() {
    private var _binding: TodoDetailFragment2Binding? = null
    private val binding: TodoDetailFragment2Binding
        get() = _binding!!
    private val vm: TodoDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = TodoDetailFragment2Binding.inflate(layoutInflater)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onPause() {
        super.onPause()
        cancelRequest()
    }

    private fun cancelRequest() {
        vm.cancelRequest()
    }

    private fun loadData() {
        vm.loadTodo()
    }

    private fun setup() {
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        vm.state.observe(viewLifecycleOwner, handleState)
    }

    private val handleState = Observer<TodoDetailState> { newState ->
        when (newState) {
            is TodoDetailState.Success -> handleSuccess(newState)
            is TodoDetailState.Loading -> handleLoading()
            is TodoDetailState.Error -> handleError(newState)
        }
        binding.viewFlipper.displayedChild = newState.ordinal
    }

    private fun handleSuccess(state: TodoDetailState.Success) {
        with (state.todo) {
            binding.success.todoTitle.text = this.title
            binding.success.todoDescription.text = this.description
        }
    }

    private fun handleLoading() {}

    private fun handleError(state: TodoDetailState.Error) {
        Toast
            .makeText(context, state.error.localizedMessage, Toast.LENGTH_SHORT)
            .show()
    }

    private fun setupView() {
        setupSuccessView()
        setupErrorView()
        setupLoadingView()
    }

    private fun setupSuccessView() {
        with (binding.success) {

        }
    }

    private fun setupErrorView() {
        with (binding.error) {
            retryBtn
                .setOnClickListener {
                    loadData()
                }
            goBackBtn
                .setOnClickListener {
                    goToHome()
                }
        }
    }

    private fun goToHome() {
        activity?.finish()
    }

    private fun setupLoadingView() {}
}