package com.example.todo_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var insertButton: Button
    private lateinit var todoEdit: EditText
    private lateinit var todoArrayList : ArrayList<Todo>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoArrayList = ArrayList()
        insertButton = findViewById(R.id.button_insert_main)
        todoEdit = findViewById(R.id.edit_todo_main)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == 980318) {
                val intent = result.data
                if (intent != null) {
                    var todoItem = todoAdapter.getmData()[intent.getIntExtra("position", 0)]
                    var editData = intent.getStringExtra("editData")
                    if (editData != null) {
                        todoItem.todoName = editData
                        todoAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
        todoAdapter = TodoAdapter(todoArrayList, activityResultLauncher)
        recyclerView.adapter = todoAdapter

        insertButton.setOnClickListener {
            val newTodo = Todo(todoEdit.text.toString())
            if (TextUtils.isEmpty(todoEdit.text.toString())) {
                Log.d("yoon: ", "저거")
                Toast.makeText(applicationContext, "할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("yoon: ", "이거")
                todoArrayList.add(newTodo)
                Log.d("todoSize: ",todoArrayList.size.toString())
                todoAdapter.addItem(newTodo)
                todoEdit.text = null
            }
        }
    }
}
