package com.eyyg.auralsphere.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.presentation.components.GenreCard
import com.eyyg.auralsphere.utils.FakeDataGenerator


class SearchFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearch: EditText = view.findViewById(R.id.etSearch)

        etSearch.requestFocus()
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // make api call here
                val glGenres: GridLayout = view.findViewById(R.id.glGenres)

                val genres = FakeDataGenerator.getGenres()
                genres.forEach {
                    glGenres.addView(GenreCard(requireContext(), it))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onDetach() {
        super.onDetach()

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}