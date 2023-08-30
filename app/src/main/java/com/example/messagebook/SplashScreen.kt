package com.example.messagebook

import android.graphics.fonts.FontFamily
import androidx.compose.ui.Alignment
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Preview
@Composable
fun SplashScreen(navController: NavController){

    val coroutineScope = rememberCoroutineScope()

    val gradient = Brush.linearGradient(
        0.0f to colorResource(id = R.color.grad1),
        200.0f to colorResource(id = R.color.grad2),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    Column(
        Modifier.fillMaxWidth()
            .background(gradient)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()
            .fillMaxWidth(.25f)
            .fillMaxHeight(.30f)

        ){
            Image(painter = painterResource(
                id = R.drawable.splash),
                contentDescription ="",
                modifier = Modifier
                    .align(androidx.compose.ui.Alignment.BottomCenter)
            )
        }
        Text(
            text = "Best Wishes",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,

                color = Color.White
            )

        )
        
    }

    LaunchedEffect(true) {
        // Wait for 3 seconds
        delay(1000)

        // Navigate to the CategoryScreen
        navController.navigate(Screen.CategoryScreen.route) {
            // Pop up to the start destination of the graph to avoid going back to the Splash screen
            popUpTo(navController.graph.startDestinationId)
            // Avoid multiple instances of the CategoryScreen in the back stack
            launchSingleTop = true
        }
    }

}