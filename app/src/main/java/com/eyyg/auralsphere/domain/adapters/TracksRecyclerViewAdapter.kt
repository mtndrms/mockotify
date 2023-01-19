package com.eyyg.auralsphere.domain.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.presentation.fragment.PlayerFragment
import com.eyyg.auralsphere.utils.Converters.toTrackDurationString

class TracksRecyclerViewAdapter(private val tracks: List<Track>) :
    RecyclerView.Adapter<TracksRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val artist: TextView
        val duration: TextView

        init {
            name = view.findViewById(R.id.tvMusicName)
            artist = view.findViewById(R.id.tvArtistName)
            duration = view.findViewById(R.id.tvDuration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = holder.itemView.context as AppCompatActivity
        val fragment = PlayerFragment()
        val bundle = Bundle()

        bundle.putString("trackID", tracks[position].id)
        bundle.putString("genre", tracks[position].genre)
        bundle.putString("trackName", tracks[position].name)
        bundle.putString("trackArtist", tracks[position].artists[0].name)
        bundle.putInt("trackDuration", tracks[position].duration_ms)
        bundle.putString("coverURL", tracks[position].album.images[0].url)
        bundle.putString("previewURL", tracks[position].preview_url)
        fragment.arguments = bundle

        holder.run {
            name.text = tracks[position].name
            artist.text = tracks[position].artists[0].name
            duration.text = tracks[position].duration_ms.toTrackDurationString()

            itemView.setOnClickListener {
                val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.run {
                    replace(R.id.fragmentContainerView, fragment, "PLAYER_FRAGMENT")
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun getItemCount(): Int = tracks.size
}