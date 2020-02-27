package com.example.android.gymondoautomationtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val listOfNames: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://wger.de/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val restService = retrofit.create(RestService::class.java)

        restService.getExerciseList().enqueue(object : Callback<Exercise> {
            override fun onFailure(call: Call<Exercise>, t: Throwable) {
                Toast.makeText(this@ListActivity, "Call failed", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                if (response.isSuccessful) {
                    val resultList = response.body()?.results
                    resultList?.forEach {
                        it.name?.also { name -> if (name.isNotEmpty()) listOfNames.add(name) }
                        viewAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@ListActivity, "Response not successful", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(listOfNames)

        val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(recycler_view.context, LinearLayoutManager.VERTICAL)
        recycler_view.layoutManager = viewManager
        recycler_view.adapter = viewAdapter
        recycler_view.addItemDecoration(dividerItemDecoration)

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

