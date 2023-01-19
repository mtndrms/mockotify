package com.eyyg.auralsphere.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.presentation.fragment.PlayerFragment
import com.eyyg.auralsphere.presentation.fragment.TrackListFragment
import com.eyyg.auralsphere.utils.FakeDataGenerator.genreColors
import com.google.android.material.card.MaterialCardView
import java.util.*

@SuppressLint("ViewConstructor")
class GenreCard constructor(context: Context, genre: String) : MaterialCardView(context) {
    init {
        val linearLayout = LinearLayout(context)
        val tvGenre = TextView(context)
        val params = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED, 1),
            GridLayout.spec(GridLayout.UNDEFINED, 1F)
        )
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        val textViewParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(getDpValue(10f), getDpValue(10f), getDpValue(10f), getDpValue(10f))
        isClickable = true
        isCheckable = true
        radius = getDpValue(3f).toFloat()
        layoutParams = params

        linearLayout.run {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor(genreColors.random()))
            layoutParams = linearLayoutParams
        }

        textViewParams.setMargins(
            getDpValue(10f), getDpValue(10f), getDpValue(10f), getDpValue(10f)
        )

        tvGenre.layoutParams = textViewParams
        tvGenre.setTextColor(ContextCompat.getColor(context, R.color.white))
        tvGenre.text = genre.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        tvGenre.textSize = 20f
        tvGenre.typeface = ResourcesCompat.getFont(context, R.font.jost_medium)

        linearLayout.addView(tvGenre)

        addView(linearLayout)

        setOnClickListener {
            val activity = context as AppCompatActivity
            val fragment = TrackListFragment()
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            val bundle = Bundle()

            bundle.putString("genre", genre.lowercase())
            fragment.arguments = bundle

            fragmentTransaction.run {
                replace(R.id.fragmentContainerView, fragment, "TRACK_LIST_FRAGMENT")
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