package com.example.messagebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun SubCategoryPreview() {
    val categoryId = "category_birthday"
    SubCategory(navController = rememberNavController(),categoryId)
}

@Composable
fun SubCategory(navController: NavController,categoryId: String){
    val gradient = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card2color1),
        200.0f to colorResource(id = R.color.card4color2),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient1 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card5color2),
        200.0f to colorResource(id = R.color.card5color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient2 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card1color2),
        200.0f to colorResource(id = R.color.card3color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient3 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card6color2),
        200.0f to colorResource(id = R.color.card5color2),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient4 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card3color2),
        200.0f to colorResource(id = R.color.card2color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient5 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card4color1),
        200.0f to colorResource(id = R.color.card6color2),
        start = Offset.Infinite,
        end = Offset.Zero
    )

    val imageResource1 = painterResource(id = R.drawable.friend)
    val imageResource2 = painterResource(id = R.drawable.love)
    val imageResource3 = painterResource(id = R.drawable.husband)
    val imageResource4 = painterResource(id = R.drawable.sisters)
    val imageResource5 = painterResource(id = R.drawable.baby)
    val imageResource6 = painterResource(id = R.drawable.mother)
    TopBarItem(navController,categoryId)

    Surface(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState())) {
            TopBarItem(navController,"categoryId")
            println(categoryId)

//            Spacer(modifier = Modifier.padding(0.dp))
            SubCategoryCard(text="Best Friend Wish",gradient1,imageResource1,categoryId = "bestFriend",navController)
            SubCategoryCard(text="BF/GF Wish",gradient,imageResource2,categoryId = "bf&gf",navController)
            SubCategoryCard(text="Husband Wife Wish",gradient2,imageResource3,categoryId = "husband",navController)
            SubCategoryCard(text="Sisters Wish",gradient3,imageResource4,categoryId = "sister",navController)
            SubCategoryCard(text="Father Wish",gradient4,imageResource5,categoryId = "father",navController)
            SubCategoryCard(text="Mother Wish",gradient5,imageResource6,categoryId = "mother",navController)

        }

    }
}

@Composable
fun SubCategoryCard(text:String,gradient:Brush,image:Painter,categoryId: String,navController:NavController){
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(10.dp, 5.dp, 10.dp, 5.dp)
            .shadow(
                elevation = 0.dp,
            ),
        shape = RoundedCornerShape(8.dp),
//        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .clickable {
                    navController.navigate("${Screen.MessageListScreen.route}/$categoryId")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)

                )
            Image(
                painter = image,
                contentDescription = "Custom Icon",

            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Forward Icon",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp, start = 6.dp)
            )

        }
    }
}