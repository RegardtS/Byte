package com.regi.abyte

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.regi.abyte.model.Location

class LocationListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<Location>()

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val latitudeTextView: TextView = itemView.findViewById(R.id.latitudeTextView)
        val longitudeTextView: TextView = itemView.findViewById(R.id.longitudeTextView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val current = locations[position]
        holder.nameTextView.text = current.name
        holder.latitudeTextView.text ="Lat: ${current.latitude}"
        holder.longitudeTextView.text = "Long: ${current.longitude}"
        holder.cardView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, LocationDetailActivity::class.java).apply {
                putExtra(LocationDetailActivity.LOCATION_ID, current.locationId)
            }
            context.startActivity(intent)
        }
    }

    internal fun setLocations(words: List<Location>) {
        this.locations = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}