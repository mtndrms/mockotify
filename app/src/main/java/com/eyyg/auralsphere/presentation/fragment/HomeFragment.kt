package com.eyyg.auralsphere.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.ApiClient
import com.eyyg.auralsphere.data.model.Suggestion
import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.data.service.CassandraService
import com.eyyg.auralsphere.data.service.TrackService
import com.eyyg.auralsphere.domain.adapters.SuggestionRecyclerViewAdapter
import com.eyyg.auralsphere.domain.adapters.TracksRecyclerViewAdapter
import com.eyyg.auralsphere.presentation.components.PlaylistCard
import com.eyyg.auralsphere.utils.Constants
import com.eyyg.auralsphere.utils.FakeDataGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var cassandraService: CassandraService
    private lateinit var suggestions: List<Suggestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiClient = ApiClient(Constants.CASS_URL)

        val guideline: Guideline = view.findViewById(R.id.guideline3)
        val rvSuggested: RecyclerView = view.findViewById(R.id.rvSuggested)
        val hsvPlaylists: HorizontalScrollView = view.findViewById(R.id.hsvPlaylists)
        val labelNothingHere: TextView = view.findViewById(R.id.labelNothingHere)
        val labelNothingHere2: TextView = view.findViewById(R.id.labelNothingHere2)
        val tvDiscover: TextView = view.findViewById(R.id.tvDiscover)
        val llPlaylistCardsContainer: LinearLayout =
            view.findViewById(R.id.llPlaylistCardsContainer)

        val history = FakeDataGenerator.history
        history.forEach {
            llPlaylistCardsContainer.addView(PlaylistCard(requireContext(), it))
        }

        if (history.isNotEmpty()) {
            hsvPlaylists.visibility = View.VISIBLE
            labelNothingHere.visibility = View.GONE
            tvDiscover.visibility = View.GONE
        }

        cassandraService = apiClient.retrofit!!.create(CassandraService::class.java)
        val tracks = cassandraService.suggests()
        tracks.enqueue(object : Callback<List<Suggestion>> {
            override fun onResponse(call: Call<List<Suggestion>>, response: Response<List<Suggestion>>) {
                println(response.body())
                if (response.isSuccessful) {
                    suggestions = (response.body() as List<Suggestion>)
                    println(suggestions)
                }

                val suggestionsRecyclerViewAdapter = SuggestionRecyclerViewAdapter(suggestions)
                rvSuggested.layoutManager = LinearLayoutManager(requireContext())
                rvSuggested.adapter = suggestionsRecyclerViewAdapter

                if (suggestions.isNotEmpty()) {
                    rvSuggested.visibility = View.VISIBLE
                    labelNothingHere2.visibility = View.GONE
                    guideline.setGuidelinePercent(0.47f)
                } else if (history.isNotEmpty()) {
                    guideline.setGuidelinePercent(0.47f)
                }
            }

            override fun onFailure(call: Call<List<Suggestion>>, t: Throwable) {
                println(t.cause)
            }
        })
    }
}