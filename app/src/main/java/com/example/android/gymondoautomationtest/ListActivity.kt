package com.example.android.gymondoautomationtest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListActivity : AppCompatActivity() {

    private val listOfExercises: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        getExercises(createRestService())
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(recycler_view.context, LinearLayoutManager.VERTICAL)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = MyAdapter(listOfExercises)
        recycler_view.addItemDecoration(dividerItemDecoration)
    }

    private fun getExercises(restService: RestService) {
        restService.getExerciseList().enqueue(object : Callback<Exercise> {
            override fun onFailure(call: Call<Exercise>, t: Throwable) {
                Toast.makeText(this@ListActivity, "Call failed", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                if (response.isSuccessful) {
                    val resultList = response.body()?.results
                    resultList?.forEach {
                        it.name?.also { name ->
                            if (name.isNotEmpty()) {
                                val id = it.id.toString()
                                listOfExercises.add("$id $name")
                            }
                        }
                        recycler_view.adapter?.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@ListActivity, "Response not successful", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun createRestService(): RestService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://wger.de/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RestService::class.java)
    }

    private fun setupListeners() {
        btnSearch.setOnClickListener {
            recycler_view.adapter =
                MyAdapter(listOfExercises.filter { it.contains(editTxtSearch.text.toString()) })
            recycler_view.adapter?.notifyDataSetChanged()
            clearFocusAndCloseKeyboard()
        }

        btnClear.setOnClickListener {
            recycler_view.adapter = MyAdapter(listOfExercises)
            recycler_view.adapter?.notifyDataSetChanged()
            editTxtSearch.text.clear()
        }
    }

    private fun clearFocusAndCloseKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        editTxtSearch.clearFocus()
    }

    class MyAdapter(private val myDataSet: List<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder {

            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false) as TextView

            return MyViewHolder(textView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = myDataSet[position]
        }

        override fun getItemCount() = myDataSet.size
    }
}

