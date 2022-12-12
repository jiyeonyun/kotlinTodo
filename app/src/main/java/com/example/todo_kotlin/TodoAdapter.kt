package com.example.todo_kotlin

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    list: ArrayList<Todo>,
    activityResultLauncher: ActivityResultLauncher<Intent>
):RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private var activityResultLauncher: ActivityResultLauncher<Intent>
    private val mData = ArrayList<Todo>()
    private lateinit var context: Context

    init {
        if (list != null) {
            mData.addAll(list)
        }
        this.activityResultLauncher = activityResultLauncher
        Log.d("개수 ", mData.size.toString())
    }

    fun addItem(todoItem: Todo) {
        mData.add(todoItem)
        notifyDataSetChanged()
    }
    fun getmData(): ArrayList<Todo> {
        return mData
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.recylcerview_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = mData[position]
        if (itemData.isDone ) {
            holder.textviewTodoItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.doneBt.visibility = View.GONE
            holder.cancleBt.visibility = View.VISIBLE
        } else {
            holder.textviewTodoItem.paintFlags = 0
            holder.cancleBt.visibility = View.GONE
            holder.doneBt.visibility = View.VISIBLE
        }
        holder.textviewTodoItem.text = itemData.todoName

        holder.cancleBt.setOnClickListener(View.OnClickListener{
                itemData.isDone = false
                notifyDataSetChanged()
                Log.d( "onClick: ","캔슬클릭")

        })
        holder.doneBt.setOnClickListener(View.OnClickListener {
                itemData.isDone = true
                notifyDataSetChanged()
                Log.d( "onClick: ","던클릭")
            })
        holder.deleteBt.setOnClickListener(View.OnClickListener{
            if(position != RecyclerView.NO_POSITION){
                mData.removeAt(position)
                notifyDataSetChanged()
            }
        })

        holder.editBt.setOnClickListener(View.OnClickListener {
            var intent = Intent(it.context,EditActivity::class.java)
            intent.putExtra("todo_name",itemData.todoName)
            intent.putExtra("todo_position",position)
            activityResultLauncher.launch(intent)
        })
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textviewTodoItem: TextView
        var deleteBt: Button
        var doneBt: Button
        var cancleBt: Button
        var editBt: Button

        init {
            editBt = itemView.findViewById(R.id.button_todo_item_edit)
            textviewTodoItem = itemView.findViewById(R.id.textview_todo_item)
            deleteBt = itemView.findViewById(R.id.button_todo_item)
            doneBt = itemView.findViewById(R.id.button_todo_item_done)
            cancleBt = itemView.findViewById(R.id.button_todo_item_cancle)
        }
    }
}
