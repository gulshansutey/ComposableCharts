package com.gulshansutey.composablecharts.charts

import android.content.res.Resources
import android.util.TypedValue
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

@Composable
fun PieChart(
    tracks: List<Track>,
    modifier: Modifier = Modifier,
    trackThickness: Float = 10f,
    animDuration: Int = 1000
) {
    val pieView = PieView(trackThickness.toPx)
    var tracksValue = 0f
    tracks.forEach { tracksValue += it.value }
    val animSpecs = FloatTweenSpec(animDuration, 0, FastOutSlowInEasing)
    val animProgress = remember(tracks) { Animatable(initialValue = 0f) }
    LaunchedEffect(tracks) {
        animProgress.animateTo(1f, animSpecs)
    }
    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startAngle = 0f
            tracks.forEach {
                val angle = 360.0f * (it.value * animProgress.value) / tracksValue
                pieView.draw(
                    it,
                    size,
                    drawContext.canvas,
                    startAngle,
                    angle
                )
                startAngle += angle
            }
        }
    }
}

private class PieView(private val thickness: Float) {

    private val trackPaint = Paint()
    init {
        trackPaint.isAntiAlias = true
        trackPaint.style = PaintingStyle.Stroke
    }

    fun draw(
        track: Track,
        size: Size,
        canvas: Canvas,
        startAngle: Float,
        sweepAngle: Float
    ) {
        val rectContainer = prepareContainerWithBounds(size, thickness)

        trackPaint.color = track.color
        trackPaint.strokeWidth = thickness

        canvas.drawArc(
            rect = rectContainer,
            paint = trackPaint,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false
        )

    }

    private fun prepareContainerWithBounds(size: Size, trackSize: Float): Rect {
        val trackBounds = trackSize.asHalf()
        val containerBounds = (size.width - size.height).asHalf()
        return Rect(
            left = trackBounds + containerBounds,
            top = trackBounds,
            right = size.width - trackBounds - containerBounds,
            bottom = size.height - trackBounds
        )
    }
}

data class Track(
    val value: Float,
    val color: Color
)

fun Float.asHalf() = this / 2
val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)
