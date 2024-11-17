package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val data: MutableList<Contact>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // ViewHolder 負責儲存並管理每個項目的視圖
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)

        // 將資料綁定到視圖，並設定刪除按鈕的點擊事件
        fun bind(item: Contact, onDelete: (Contact) -> Unit) {
            tvName.text = item.name
            tvPhone.text = item.phone
            imgDelete.setOnClickListener { onDelete(item) }
        }
    }

    // 建立 ViewHolder 並連結對應的 Layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ViewHolder(view)
    }

    // 傳回資料數量
    override fun getItemCount(): Int = data.size

    // 將資料綁定到 ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position]) { item ->
            // 刪除資料並通知 RecyclerView 更新
            data.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, data.size)
        }
    }
}
