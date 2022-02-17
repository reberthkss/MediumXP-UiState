package br.com.dialogystudios.tododelete

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dialogystudios.tododelete.databinding.TodoDeleteActivityBinding
import br.com.dialogystudios.tododelete.presentation.vm.TodoDeleteViewModel

class TodoDeleteActivity: AppCompatActivity() {
    private var _binding: TodoDeleteActivityBinding? = null
    private val binding: TodoDeleteActivityBinding
        get() = _binding!!
    private val vm: TodoDeleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = TodoDeleteActivityBinding.inflate(layoutInflater)
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

    private fun setupViewModel() {
        intent.data?.getQueryParameter("id")?.let {
            vm.setTodoID(it)
        }
    }

    private fun setupToolbar() {
        with (binding.toolbarContainer.toolbar) {
            setTitle(R.string.toolbar_delete_title)
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}