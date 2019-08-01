package com.regi.abyte

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_location_detail.*

class LocationDetailActivity : AppCompatActivity() {

    companion object {
        const val LOCATION_ID = "LOCATION_ID"
    }

    private lateinit var viewModel: LocationDetailViewModel
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProviders.of(this).get(LocationDetailViewModel::class.java)

        index = intent.getIntExtra(LOCATION_ID, -1)
        if (index >= 0) {
            updatingLocation()
        }

        saveButton.setOnClickListener {
            onSavedClicked()
        }
    }

    private fun onSavedClicked(){
        val nameString = nameEditText.text.toString()
        val latString = latEditText.text.toString()
        val longString = longEditText.text.toString()

        if (!checkValidName(nameString)) {
            nameEditText.error = "Invalid length"

        } else if (latString.isEmpty()) {
            latEditText.error = "Can't be empty"
        } else if (longString.isEmpty()) {
            longEditText.error = "Can't be empty"
        } else {
            viewModel.updateLocationDetails(
                nameString,
                latString.toDouble(),
                longString.toDouble()
            )
            finish()
        }
    }

    private fun checkValidName(string: String): Boolean {
        return string.length in 1..99
    }

    private fun updatingLocation() {
        viewModel.existingLocation(index)
        viewModel.location.observe(this, Observer {
            latEditText.setText(it?.latitude.toString())
            longEditText.setText(it?.longitude.toString())
            nameEditText.setText(it?.name)
            saveButton.text = "Update"
            invalidateOptionsMenu()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (index >= 0) {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_details, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_location -> {
                viewModel.deleteLocation()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
