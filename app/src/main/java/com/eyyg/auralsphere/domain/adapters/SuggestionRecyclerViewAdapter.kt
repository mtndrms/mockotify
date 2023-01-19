package com.eyyg.auralsphere.domain.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.ApiClient
import com.eyyg.auralsphere.data.model.Suggestion
import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.data.service.TrackService
import com.eyyg.auralsphere.presentation.fragment.PlayerFragment
import com.eyyg.auralsphere.utils.Constants
import com.eyyg.auralsphere.utils.Converters.toTrackDurationString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuggestionRecyclerViewAdapter(private val tracks: List<Suggestion>) :
    RecyclerView.Adapter<SuggestionRecyclerViewAdapter.ViewHolder>() {
    private lateinit var trackService: TrackService
    private lateinit var oneTrack: List<Track>

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
        val apiClient = ApiClient(Constants.API_URL)

        trackService = apiClient.retrofit!!.create(TrackService::class.java)
        val id = tracks[position].target_id
        val track = trackService.getTrackByID(id)
        track.enqueue(object : Callback<List<Track>> {
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                if (response.isSuccessful) {
                    println("TEST1")
                    oneTrack = (response.body() as List<Track>)
                    println(oneTrack)

                    val activity = holder.itemView.context as AppCompatActivity
                    val fragment = PlayerFragment()
                    val bundle = Bundle()

                    bundle.putString("trackID", oneTrack[0].id)
                    bundle.putString("genre", oneTrack[0].genre)
                    bundle.putString("trackName", oneTrack[0].name)
                    bundle.putString("trackArtist", oneTrack[0].artists[0].name)
                    bundle.putInt("trackDuration", oneTrack[0].duration_ms)
                    bundle.putString("coverURL", oneTrack[0].album.images[0].url)
                    bundle.putString("previewURL", oneTrack[0].preview_url)
                    fragment.arguments = bundle

                    holder.run {
                        name.text = oneTrack[0].name
                        artist.text = oneTrack[0].artists[0].name
                        duration.text = oneTrack[0].duration_ms.toTrackDurationString()

                        itemView.setOnClickListener {
                            val fragmentTransaction =
                                activity.supportFragmentManager.beginTransaction()
                            fragmentTransaction.run {
                                replace(R.id.fragmentContainerView, fragment, "PLAYER_FRAGMENT")
                                addToBackStack(null)
                                commit()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                println("TEST")
                println(t.message.toString())
            }
        })
    }

    override fun getItemCount(): Int = tracks.size
}