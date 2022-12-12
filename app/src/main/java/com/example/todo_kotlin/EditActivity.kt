package com.example.todo_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    lateinit var editDoneButton: Button
    lateinit var todoEdit: EditText
    lateinit var todoName: String
    var todoPosition : Int = 0
    lateinit var editValue: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        editDoneButton = findViewById(R.id.button_editDone_edit)
        todoEdit = findViewById(R.id.edit_todo_edit)
        todoName = intent.getStringExtra("todo_name").toString()
        todoPosition = intent.getIntExtra("todo_position",0)

        todoEdit.setText(todoName)

        editDoneButton.setOnClickListener(View.OnClickListener{
            val intent = Intent()
            intent.putExtra("editData", todoEdit.text.toString())
            intent.putExtra("position", todoPosition)
            setResult(980318, intent)
            finish()
        })
    }
}