package com.ludosupreme.utils.textdecorator

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomCharacterSpan : MetricAffectingSpan {
    var ratio = 0.5

    constructor()
    constructor(ratio: Double) {
        this.ratio = ratio
    }

    override fun updateDrawState(paint: TextPaint) {
        paint.baselineShift += (paint.ascent() * ratio).toInt()
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.baselineShift += (paint.ascent() * ratio).toInt()
    }
}