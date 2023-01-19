package com.eyyg.auralsphere.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.presentation.components.GenreCard
import com.eyyg.auralsphere.utils.FakeDataGenerator

class DiscoverFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btSearch: LinearLayout = view.findViewById(R.id.btSearch)
        val glGenres: GridLayout = view.findViewById(R.id.glGenres)

        val genres = FakeDataGenerator.getGenres()
        genres.forEach {
            glGenres.addView(GenreCard(requireContext(), it))
        }

        btSearch.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

            fragmentTransaction.run {
                setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                replace(R.id.fragmentContainerView, SearchFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
}