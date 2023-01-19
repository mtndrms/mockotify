package com.eyyg.auralsphere.data.model

import java.sql.Timestamp

data class Suggestion(
    val refer_id: String,
    val target_id: String,
    val referred_at: String
)
