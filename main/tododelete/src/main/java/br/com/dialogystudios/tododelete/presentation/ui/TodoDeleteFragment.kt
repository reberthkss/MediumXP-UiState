package br.com.dialogystudios.tododelete.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.dialogystudios.tododelete.R
import br.com.dialogystudios.tododelete.databinding.TodoDeleteFragmentBinding
import br.com.dialogystudios.tododelete.presentation.vm.TodoDeleteState
import br.com.dialogystudios.tododelete.presentation.vm.TodoDeleteViewModel
import kotlinx.android.synthetic.main.todo_delete_normal_fragment.todo_delete_msg

class TodoDeleteFragment: Fragment() {
    private var _binding: TodoDeleteFragmentBinding? = null
    private val binding: TodoDeleteFragmentBinding
        get() = _binding!!
    private val vm: TodoDeleteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoDeleteFragmentBinding.inflate(layoutInflater)
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setup() {
        setupView()
        setupObserver()
    }

    private fun setupView() {
        setupNormalView()
        setupSuccessView()
        setupErrorView()
    }

    private fun setupNormalView() {
        with(binding.normal) {
            todoDeleteMsg.text = getString(R.string.todo_delete_message)
            confirmBtn
                .setOnClickListener {
                    removeTodo()
                }

            goBackBtn
                .setOnClickListener {
                    goToHome()
                }
        }
    }

    private fun setupSuccessView() {
        with (binding.success) {
            goToHome
                .setOnClickListener {
                    goToHome()
                }
        }
    }

    private fun setupErrorView() {
        with (binding.error) {
            retryBtn
                .setOnClickListener {
                   removeTodo()
                }
            goToHomeBtn
                .setOnClickListener {
                    goToHome()
                }
        }
    }

    private fun removeTodo() {
        vm.delete()
    }

    private fun goToHome() {
        Intent()
            .apply {
                putExtra("was_removed", true)
                activity?.setResult(Activity.RESULT_OK, this)
                activity?.finish()
            }
    }

    private fun setupObserver() {
        vm.state.observe(viewLifecycleOwner, handleState)
    }

    private val handleState = Observer<TodoDeleteState> { newState ->
        when (newState) {
            is TodoDeleteState.Normal -> handleNormal()
            is TodoDeleteState.Success -> handleSuccess()
            is TodoDeleteState.Loading -> handleLoading()
            is TodoDeleteState.Error -> handleError(newState)
        }
        binding.viewFlipper.displayedChild = newState.ordinal
    }

    private fun handleNormal() {}

    private fun handleSuccess() {}

    private fun handleLoading() {}

    private fun handleError(state: TodoDeleteState.Error) {
        Toast
            .makeText(context, state.error.localizedMessage, Toast.LENGTH_SHORT)
            .show()
    }
}