package br.com.dialogystudios.tododetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.dialogystudios.tododetail.contracts.DeleteTodoContract
import br.com.dialogystudios.tododetail.contracts.UpdateTodoContract
import br.com.dialogystudios.tododetail.databinding.ActivityTodoDetailBinding
import br.com.dialogystudios.tododetail.presentation.vm.TodoDetailViewModel

class TodoDetailActivity : AppCompatActivity() {
    private var _binding: ActivityTodoDetailBinding? = null
    private val binding: ActivityTodoDetailBinding
        get() = _binding!!
    private val vm: TodoDetailViewModel by viewModels()
    private val deleteActivityLauncher = registerForActivityResult(DeleteTodoContract()) { wasRemoved ->
        if (wasRemoved) finish()
    }
    private val updateActivityLauncher = registerForActivityResult(UpdateTodoContract()) { wasUpdated ->
        if (wasUpdated) vm.loadTodo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup() {
        setupToolbar()
        setupViewModel()
    }

    private fun setupToolbar() {
        with(binding.toolbarContainer.toolbar) {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> goToDelete()
                    R.id.update -> goToUpdate()
                }
                true
            }
            setNavigationOnClickListener {
                finish()
            }
            setTitle(R.string.toolbar_detail_title)
            inflateMenu(R.menu.toolbar_menu)
        }
    }

    private fun goToDelete() {
        deleteActivityLauncher.launch(vm.id.value)
    }

    private fun goToUpdate() {
        updateActivityLauncher.launch(vm.id.value)
    }

    private fun setupViewModel() {
        intent.data?.getQueryParameter("id")?.let {
            vm.setId(it)
        }
    }
}