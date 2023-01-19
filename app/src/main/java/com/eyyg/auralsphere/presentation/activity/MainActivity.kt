package com.eyyg.auralsphere.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.ApiClient
import com.eyyg.auralsphere.data.model.TrackResponse
import com.eyyg.auralsphere.data.service.TrackService
import com.eyyg.auralsphere.presentation.fragment.AboutFragment
import com.eyyg.auralsphere.presentation.fragment.DiscoverFragment
import com.eyyg.auralsphere.presentation.fragment.HomeFragment
import com.eyyg.auralsphere.utils.Constants
import com.eyyg.auralsphere.utils.Helpers.changeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var trackService: TrackService
    private lateinit var trackResponse: TrackResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    changeFragment(fragment, this)
                    true
                }
                R.id.discover -> {
                    val fragment = DiscoverFragment()
                    changeFragment(fragment, this)
                    true
                }
                R.id.about -> {
                    val fragment = AboutFragment()
                    changeFragment(fragment, this)
                    true
                }
                else -> false
            }
        }

        val apiClient = ApiClient(Constants.API_URL)

        trackService = apiClient.retrofit!!.create(TrackService::class.java)
        val tracks = trackService.getTracks(1, 5)
        tracks.enqueue(object : Callback<TrackResponse> {
            override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                if (response.isSuccessful) {
                    trackResponse = (response.body() as TrackResponse)
                }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                println(t.message.toString())
            }
        })
    }
}