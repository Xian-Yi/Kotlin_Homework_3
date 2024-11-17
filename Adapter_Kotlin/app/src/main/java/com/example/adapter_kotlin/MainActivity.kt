package com.example.adapter_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    data class Data(val photo: Int, val name: String)

    inner class MyAdapter(private val data: Array<Data>, private val layoutId: Int) : BaseAdapter() {
        override fun getCount(): Int = data.size
        override fun getItem(position: Int): Any = data[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity).inflate(layoutId, parent, false)
            val name = view.findViewById<TextView>(R.id.name)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            name.text = data[position].name
            imageView.setImageResource(data[position].photo)
            return view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val transNameArray = arrayOf("腳踏車", "機車", "汽車", "巴士", "飛機", "輪船")
        val transPhotoIdArray = intArrayOf(
            R.drawable.trans1, R.drawable.trans2,
            R.drawable.trans3, R.drawable.trans4,
            R.drawable.trans5, R.drawable.trans6
        )
        val transData = transNameArray.mapIndexed { index, name ->
            Data(photo = transPhotoIdArray[index], name = name)
        }.toTypedArray()

        val transAdapter = MyAdapter(transData, R.layout.trans_list)
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = transAdapter

        val cubeeNameArray = arrayOf(
            "哭哭", "發抖", "再見", "生氣", "昏倒", "竊笑",
            "很棒", "你好", "驚嚇", "大笑"
        )
        val cubeePhotoIdArray = intArrayOf(
            R.drawable.cubee1, R.drawable.cubee2,
            R.drawable.cubee3, R.drawable.cubee4,
            R.drawable.cubee5, R.drawable.cubee6,
            R.drawable.cubee7, R.drawable.cubee8,
            R.drawable.cubee9, R.drawable.cubee10
        )
        val cubeeData = cubeeNameArray.mapIndexed { index, name ->
            Data(photo = cubeePhotoIdArray[index], name = name)
        }.toTypedArray()

        val cubeeAdapter = MyAdapter(cubeeData, R.layout.cubee_list)
        val gridView: GridView = findViewById(R.id.gridView)
        gridView.adapter = cubeeAdapter
        gridView.numColumns = 3

        val messageArray = arrayOf("訊息1", "訊息2", "訊息3", "訊息4", "訊息5", "訊息6")
        val messageAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messageArray)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = messageAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
