package com.regi.abyte

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.regi.abyte.background.BackgroundWorker
import com.regi.abyte.model.Location
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var viewModelWeather: WeatherListViewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var workManager:WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = LocationListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, LocationDetailActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModelWeather = ViewModelProviders.of(this).get(WeatherListViewModel::class.java)


        viewModel.allLocations.observe(this, Observer { list ->
            adapter.setLocations(list)
            list.isEmpty().let {
                progressBar.visibility = View.GONE
            }
        })

        viewModelWeather.weather.observe(this, Observer {
            val currentTime = SimpleDateFormat("HH:mm:ss").format(Date())
            supportActionBar?.title = "$it °C"
            supportActionBar?.subtitle = "Retrieved at $currentTime"
        })


        checkInitialStartup()
    }

    private fun checkInitialStartup() {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val tag = "dummy_data"
        val hasAddedDemoData = prefs.getBoolean(tag, false)
        if (!hasAddedDemoData) {
            bombData()
            editor.putBoolean(tag, true).apply()
        }
    }

    private fun bombData() {
        val list = mutableListOf<Location>()
        for (x in 0..500) {
            val tempLocation = Location("testLocation - $x", x.toDouble(), x.toDouble())
            list.add(tempLocation)
        }
        viewModel.insertBulk(list)
    }

    private fun setupBackgroundWorker() {
        workManager = WorkManager.getInstance(applicationContext)
        val refreshRequest = PeriodicWorkRequest.Builder(
            BackgroundWorker::class.java,
            30,
            TimeUnit.MINUTES
        ).build()
        workManager.enqueue(refreshRequest)
    }

    private fun shutdownBackgroundWorker(){
        workManager.cancelAllWork()
    }
}
