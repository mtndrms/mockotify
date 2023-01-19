package com.eyyg.auralsphere.utils

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eyyg.auralsphere.R
import com.eyyg.auralsphere.data.model.Track
import com.eyyg.auralsphere.presentation.activity.MainActivity
import com.eyyg.auralsphere.utils.FakeDataGenerator.history
import java.io.IOException


object Helpers {
    fun changeFragment(fragment: Fragment, activity: MainActivity) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.run {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack(null)
            commit()
        }
    }

    var tmp: Bundle = Bundle()
}