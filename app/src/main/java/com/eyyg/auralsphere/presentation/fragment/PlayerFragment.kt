package com.eyyg.auralsphere.presentation.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.ApiClient
import com.eyyg.auralsphere.data.model.Suggestion
import com.eyyg.auralsphere.data.service.CassandraService
import com.eyyg.auralsphere.utils.Constants
import com.eyyg.auralsphere.utils.Converters.toTrackDurationString
import com.eyyg.auralsphere.utils.FakeDataGenerator.history
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayerFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var cassandraService: CassandraService
    private lateinit var recommend: List<Suggestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaPlayer = MediaPlayer()

        val genre = requireArguments().getString("genre", "null")
        val trackID = requireArguments().getString("trackID", "null")
        val trackName = requireArguments().getString("trackName", "null")
        val trackArtist = requireArguments().getString("trackArtist", "null")
        val trackDuration = requireArguments().getInt("trackDuration", 0)
        val coverURL = requireArguments().getString("coverURL", "null")
        val previewURL = requireArguments().getString("previewURL", "null")

        val ivBack: ImageView = view.findViewById(R.id.ivBack)
        val tvTrackName: TextView = view.findViewById(R.id.tvTrackName)
        val tvTrackArtist: TextView = view.findViewById(R.id.tvTrackArtist)
        val tvTrackDuration: TextView = view.findViewById(R.id.tvTrackDuration)
        val ivTrackCover: ImageView = view.findViewById(R.id.ivTrackCover)
        val btPlayPauseResume: ImageView = view.findViewById(R.id.btPlayPauseResume)
        val tvPlaylistName: TextView = view.findViewById(R.id.tvPlaylistName)

        tvTrackName.text = trackName
        tvTrackArtist.text = trackArtist
        tvTrackDuration.text = trackDuration.toTrackDurationString()
        tvPlaylistName.text = genre

        val apiClient = ApiClient(Constants.CASS_URL)
        cassandraService = apiClient.retrofit!!.create(CassandraService::class.java)
        cassandraService.recommend(trackID).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("RECOMMENDED!")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("FAILED!")
            }
        })

        Picasso.get().load(coverURL).resize(250, 250).centerCrop().into(ivTrackCover)

        mediaPlayer.setDataSource(previewURL)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        btPlayPauseResume.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause();
                mediaPlayer.reset();
                mediaPlayer.release();
                btPlayPauseResume.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_play
                    )
                )
            } else {
                mediaPlayer.prepare()
                mediaPlayer.start()
                btPlayPauseResume.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_pause
                    )
                )
            }
        }

        ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        history.add(requireArguments())
    }
}