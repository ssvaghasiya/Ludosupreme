package com.ludosupreme.ui.home.model

data class TournamentsData(
    var player: String,
    var entry: String,
    var prizePool: String,
    var rank1: String? = null,
    var rank2: String? = null,
    var rank3: String? = null,
    var rank4: String? = null,
    var time: Long = 120000,
    var isFinished: Boolean = false,
    var userCount: Int = 0
)