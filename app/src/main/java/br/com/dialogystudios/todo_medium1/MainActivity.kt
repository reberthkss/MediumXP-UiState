package br.com.dialogystudios.todo_medium1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.dialogystudios.todolist.TodoListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, TodoListActivity::class.java)
            .apply {
                startActivity(this)
            }
            .also {
                finish()
            }
    }

}