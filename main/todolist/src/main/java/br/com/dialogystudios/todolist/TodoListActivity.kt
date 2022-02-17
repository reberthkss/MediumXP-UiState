package br.com.dialogystudios.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.dialogystudios.todolist.databinding.ActivityTodoListBinding

class TodoListActivity : AppCompatActivity() {
    private var _binding: ActivityTodoListBinding? = null
    private val binding: ActivityTodoListBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =  ActivityTodoListBinding.inflate(layoutInflater)
        setup()
        setContentView(binding.root)
    }

    private fun setup() {
        setupToolbar()
        setupObserever()
        setupView()
    }

    private fun setupObserever() {

    }

    private fun setupView() {

    }

    private fun setupToolbar() {
        with (binding.toolbarContainer.toolbar) {
            setTitle(R.string.toolbar_list_title)
            setNavigationIcon(null)
        }
    }

}
