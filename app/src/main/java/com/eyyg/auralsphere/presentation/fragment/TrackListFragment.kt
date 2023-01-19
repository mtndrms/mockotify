package com.eyyg.auralsphere.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.ApiClient
import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.data.service.TrackService
import com.eyyg.auralsphere.domain.adapters.TracksRecyclerViewAdapter
import com.eyyg.auralsphere.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TrackListFragment : Fragment() {
    private lateinit var trackService: TrackService
    private lateinit var filteredTrackResponse: List<Track>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genre = requireArguments().getString("genre", "null")
        val apiClient = ApiClient(Constants.API_URL)

        val rvTracks: RecyclerView = view.findViewById(R.id.rvTracks)
        val tvPlaylistName: TextView = view.findViewById(R.id.tvPlaylistName)
        val ivBack: ImageView = view.findViewById(R.id.ivBack)

        tvPlaylistName.text =
            genre.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        trackService = apiClient.retrofit!!.create(TrackService::class.java)
        val tracks = trackService.getTracksFilteredByGenre(genre)
        tracks.enqueue(object : Callback<List<Track>> {
            override fun onResponse(
                call: Call<List<Track>>,
                response: Response<List<Track>>
            ) {
                if (response.isSuccessful) {
                    filteredTrackResponse = (response.body() as List<Track>)
                    val tracksRecyclerViewAdapter =
                        TracksRecyclerViewAdapter(filteredTrackResponse)
                    rvTracks.layoutManager = LinearLayoutManager(requireContext())
                    rvTracks.adapter = tracksRecyclerViewAdapter
                }
            }

            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                println(t.message.toString())
            }
        })

        ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}