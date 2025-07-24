package com.example.composeroutemap.utils


/** 1000M 이상 거리 자동 km 표기*/
fun Int?.formatMeter(): String = when {
    this == null      -> "???"
    this < 1_000      -> "${this}m"
    else              -> String.format("%.1fkm", this / 1_000.0)
}