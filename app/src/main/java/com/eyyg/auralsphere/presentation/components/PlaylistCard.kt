package com.eyyg.auralsphere.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.presentation.fragment.PlayerFragment
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

@SuppressLint("ViewConstructor")
class PlaylistCard constructor(context: Context, bundle: Bundle) : MaterialCardView(context) {
    init {
        val genre = bundle.getString("genre", "null")
        val trackName = bundle.getString("trackName", "null")
        val coverURL = bundle.getString("coverURL", "null")

        val linearLayout = LinearLayout(context)
        val tvPlaylistName = TextView(context)
        val ivPlaylistCover = ImageView(context)

        val params = LayoutParams(
            resources.getDimension(R.dimen.playlist_card_width).toInt(),
            resources.getDimension(R.dimen.playlist_card_height).toInt()
        )
        params.marginEnd = getDpValue(10f)
        isClickable = true
        isCheckable = true
        radius = getDpValue(7f).toFloat()

        layoutParams = params

        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.run {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(ContextCompat.getColor(context, R.color.background))
            layoutParams = linearLayoutParams
        }

        val ivLayoutParams = ViewGroup.LayoutParams(getDpValue(150f), getDpValue(150f))

        ivPlaylistCover.contentDescription = resources.getString(R.string.playlist_cover)
        ivPlaylistCover.layoutParams = ivLayoutParams

        Picasso.get()
            .load(coverURL)
            .resize(150, 190)
            .centerCrop()
            .into(ivPlaylistCover)

        val textViewParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textViewParams.setMargins(getDpValue(0f), getDpValue(5f), getDpValue(0f), getDpValue(5f))

        tvPlaylistName.layoutParams = textViewParams
        tvPlaylistName.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
        tvPlaylistName.text = trackName
        tvPlaylistName.textSize = 18f
        tvPlaylistName.typeface = ResourcesCompat.getFont(context, R.font.jost_regular)

        linearLayout.addView(ivPlaylistCover)
        linearLayout.addView(tvPlaylistName)

        addView(linearLayout)

        setOnClickListener {
            val activity = context as AppCompatActivity
            val fragment = PlayerFragment()
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()

            fragment.arguments = bundle
            fragmentTransaction.run {
                replace(R.id.fragmentContainerView, fragment, "PLAYER_FRAGMENT")
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun getDpValue(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics
        ).toInt()
    }
}