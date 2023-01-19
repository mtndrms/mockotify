package com.eyyg.auralsphere.utils

object Converters {
    fun Int.toTrackDurationString(): String {
        val minute = this / 60000
        val second = (this % 60000) / 1000

        return "$minute:$second"
    }
}