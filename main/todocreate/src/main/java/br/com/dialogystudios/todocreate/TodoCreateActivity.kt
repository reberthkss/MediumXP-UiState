package br.com.dialogystudios.todocreate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.dialogystudios.todocreate.databinding.ActivityTodoCreateBinding
import br.com.dialogystudios.todocreate.presentation.vm.TodoCreateViewModel

class TodoCreateActivity : AppCompatActivity() {
    private var _binding: ActivityTodoCreateBinding? = null
    private val binding: ActivityTodoCreateBinding
        get() = _binding!!
    private val vm: TodoCreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup() {
        setupToolbar()
    }

    private fun setupToolbar() {
        with (binding.toolbarContainer.toolbar) {
            setTitle(R.string.toolbar_create_title)
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}