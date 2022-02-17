package br.com.dialogystudios.todoupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.dialogystudios.todoupdate.databinding.ActivityTodoUpdateBinding
import br.com.dialogystudios.todoupdate.presentation.vm.TodoUpdateViewModel

class TodoUpdateActivity : AppCompatActivity() {
    private var _binding: ActivityTodoUpdateBinding? = null
    private val binding: ActivityTodoUpdateBinding
        get() = _binding!!
    private val vm: TodoUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoUpdateBinding.inflate(layoutInflater)
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
        with (binding.toolbarContainer.toolbar) {
            setTitle(R.string.toolbar_update_title)
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupViewModel() {
        intent.data?.getQueryParameter("id")?.let {
            vm.setID(it)
        }
    }

}