package com.gulshansutey.composablecharts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gulshansutey.composablecharts.charts.PieChart
import com.gulshansutey.composablecharts.charts.Track
import com.gulshansutey.composablecharts.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableChartsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("PIE CHART")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(ColorTrackFive)
            .fillMaxSize()
    ) {
        Text(
            name,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        val tracks = listOf(
            Track(72f, ColorTrackOne),
            Track(13f, ColorTrackTwo),
            Track(5f, ColorTrackThree),
            Track(3f, ColorTrackFour),
            Track(2f, ColorTrackFive),
        )
        Card(modifier = Modifier.padding(20.dp)) {
            PieChart(
                tracks = tracks,
                modifier = Modifier
                    .size(180.dp)
                    .padding(20.dp),
                trackThickness = 30f
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposableChartsTheme {
        Greeting("PIE CHART")
    }
}