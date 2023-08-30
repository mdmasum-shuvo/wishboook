package com.example.messagebook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable

fun TwoCardsInRow(text: String, image: Int, text1: String, image1: Int,gradient: Brush,gradient1: Brush,navController: NavController,  categoryId1: String, categoryId2: String,) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp, 0.dp, 5.dp, 0.dp)
                .height(108.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
                )
                .background(gradient),
            shape = RoundedCornerShape(16.dp),

//            elevation = 4.dp
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .clickable {
//                    navController.navigate(route = Screen.MessageListScreen.route)
//                    navController.navigate("${Screen.MessageListScreen.route}/$categoryId1")
                    if (categoryId1 == "category_birthday") {
                        navController.navigate("${Screen.SubCategory}/$categoryId1")
                    }
                    else {
                        navController.navigate("${Screen.MessageListScreen.route}/$categoryId1")
                    }
                }
            )
            {
                Text(
                    text = text,
                    style = TextStyle(
                        color = Color.White, fontSize = 20.sp,
                        
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(20.dp)
                        .fillMaxSize(.7f)
                
                )
                Image(
                    painter = painterResource(image),
                    contentDescription = text,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxHeight(.40f)
                        .padding(20.dp, 0.dp, 0.dp, 5.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp, 0.dp, 10.dp, 10.dp)
                .height(108.dp)
                .shadow(
                    elevation = 10.dp,
//                    shape = RoundedCornerShape(8.dp),
                ),

            shape = RoundedCornerShape(8.dp),
//            elevation = 4.dp
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(gradient1)
                .clickable {

                    navController.navigate("${Screen.MessageListScreen.route}/$categoryId2")
                }
            )

            {
                Text(
                    text = text1,
                    style = TextStyle(
                        color = Color.White, fontSize = 20.sp,

                        ),
                    modifier = Modifier
//                        .padding(10.dp)
                        .fillMaxWidth(.9f)
                        .align(Alignment.TopStart)
                        .padding(20.dp)
                )
                Image(
                    painter = painterResource(image1),
                    contentDescription = text1,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxHeight(.40f)
                        .padding(20.dp, 0.dp, 0.dp, 5.dp)
                )
            }
        }
    }
}


@Composable
fun HomePage(navController: NavController){
    val gradient = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card1color1),
        200.0f to colorResource(id = R.color.card1color2),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient1 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card2color2),
        200.0f to colorResource(id = R.color.card2color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient2 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card3color2),
        200.0f to colorResource(id = R.color.card3color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient3 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card4color2),
        200.0f to colorResource(id = R.color.card4color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient4 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card5color2),
        200.0f to colorResource(id = R.color.card5color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
    val gradient5 = Brush.linearGradient(
        0.0f to colorResource(id = R.color.card6color2),
        200.0f to colorResource(id = R.color.card6color1),
        start = Offset.Infinite,
        end = Offset.Zero
    )
//    TopCard()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        ){
        TopCard()

            TwoCardsInRow(text = stringResource(id = R.string.birthday), image = R.drawable.splash,text1 = stringResource(id = R.string.anniversary), image1 = R.drawable.splash,gradient = gradient,gradient1 = gradient1,navController,categoryId1 = "category_birthday", categoryId2 = "category_anniversary")
            TwoCardsInRow(text = stringResource(id = R.string.health), image = R.drawable.splash,text1 = stringResource(id = R.string.christmas), image1 = R.drawable.splash,gradient = gradient2,gradient1 = gradient3,navController,categoryId1 = "category_health", categoryId2 = "category_chirsmas")
            TwoCardsInRow(text = stringResource(id = R.string.motivation), image = R.drawable.splash,text1 = stringResource(id = R.string.success), image1 = R.drawable.splash,gradient = gradient4,gradient1 = gradient5,navController,categoryId1 = "category_motivation", categoryId2 = "category_success",)
            BannerAd(adUnitId = "ca-app-pub-9642459238169402/2822319167")
    }


}

@Composable
fun TopCard(){
   Box(
       Modifier
           .fillMaxWidth()
           .padding(0.dp, 0.dp, 0.dp, 10.dp)
           .fillMaxWidth()
           .height(48.dp)
           .shadow(
               elevation = 4.dp,
               shape = MaterialTheme.shapes.small.copy(ZeroCornerSize)
           ) // Add bottom shadow
           .background(Color.White)
   ) {
       Text(
           "Best Wishes",
          modifier = Modifier
//               .padding(0.dp,5.dp,0.dp,0.dp)
               .align(Alignment.Center),
           style = TextStyle(
               fontWeight = FontWeight.Bold,
               fontSize = 16.sp,

               color = Color.Black
           )
       )
       
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerAd(adUnitId: String) {
    Box(
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .fillMaxSize(),
        contentAlignment =  Alignment.BottomCenter


    ) {

            AndroidView(factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    this.adUnitId  = adUnitId
                    loadAd(AdRequest.Builder().build())
                }
            })

        }



}