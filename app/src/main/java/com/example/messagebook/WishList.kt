package com.example.messagebook

import android.content.ActivityNotFoundException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController


@Composable
fun ListHome(navController: NavController,categoryId: String = ""){

    Column(Modifier.background(Color.White)) {

        TopBarItem(navController, categoryId)

        val texts = getTextsForCategory(categoryId)

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(texts) { text ->
                CustomCard(text)
            }
        }

    }


}


@Composable
fun TopBarItem(navController: NavController,categoryId: String?){
    val showBackArrow = categoryId != null
    Box(
        Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .fillMaxWidth()
            .height(48.dp)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.small.copy(ZeroCornerSize)
            ) // Add bottom shadow
            .background(Color.White)

    ) {
        Row(
            Modifier
                .height(48.dp)
                .fillMaxSize()
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically

        ){

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
//                    tint = MaterialTheme.colors.onSurface
                Modifier.align(Alignment.CenterVertically)
                    .clickable {
                        if (showBackArrow) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(route = Screen.CategoryScreen.route)
                        }
                    }

                )

            Text(
                text = if(categoryId=="category_birthday") "Birthday Wishes" else if(categoryId=="category_anniversary") "Anniversary Wishes"
                else if(categoryId=="category_health") "Health Wishes" else if(categoryId=="category_chirsmas") "Christmas/Eid Wishes"
                else if (categoryId=="category_motivation") "Motivation Wishes" else if (categoryId=="category_success") "Success Wishes"
                else if (categoryId=="bestFriend") " Best Friend Wishes"
                else if (categoryId=="bf&gf") "BF & GF Wish"
                else if (categoryId=="husband") "Husband Wish"
                else if (categoryId=="sister") "Sister Wish"
                else if (categoryId=="father") "Father Wish"
                else if (categoryId=="mother") "Mother Wish"
                else "Success Wishes",
                Modifier.padding(16.dp,0.dp,0.dp,0.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

            )
        }



    }
 }

@Composable
fun CustomCard(text: String) {

    val clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    var isCopied by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(min = 117.dp)
            .padding(0.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Box (
            Modifier
                .fillMaxSize()
                .background(Color.White))
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = text,
                    Modifier.padding(16.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

            Row(
                modifier = Modifier
                    .fillMaxWidth(.7f)
//                    .align(Alignment.BottomStart)
                    .padding(10.dp, 0.dp, 0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        val clipData = android.content.ClipData.newPlainText("Copied Text", text)
                        clipboardManager.setPrimaryClip(clipData)
                        isCopied = true
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.btnclr2)),
                    shape = RoundedCornerShape(10.dp),


                ) {

                    Icon(painterResource(id = R.drawable.contentcopy),contentDescription = "",)
                    Text(text = "Copy")
                }
                val shareLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    isCopied = false
                }

                Button(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = ACTION_SEND
                            putExtra(EXTRA_TEXT, text)
                            type = "text"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Share via")
                        try {
                            shareLauncher.launch(shareIntent)
                        } catch (e: ActivityNotFoundException) {

                        }
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.btnclr2)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "")
                    Text(text = "Share")
                }
            }
        }
    }
}}


fun getTextsForCategory(categoryId: String): List<String> {
    return when (categoryId) {
        "category_birthday" -> listOf(
            "Count your life by smiles, not tears. Count your age by friends, not years. Happy birthday!",
            "Happy birthday! I hope all your birthday wishes and dreams come true.",
            "Wishing you a day filled with love, joy, and laughter. Happy birthday!",
            "May your birthday be as special as you are. Have a fantastic day!",
            "Another year older, another reason to celebrate. Happy birthday!",
            "Sending you warmest wishes on your birthday. Enjoy your special day!",
            "Happy birthday! May this year be the best one yet.",
            "Wishing you a day filled with love, surprises, and sweet memories. Happy birthday!",
            "Happy birthday! May your heart be filled with happiness and your life with blessings.",
            "Count the candles, not the years. Happy birthday!",
            "Another year, another chapter of your remarkable life. Happy birthday!",
            "Wishing you a day full of love, laughter, and everything you desire. Happy birthday!",
            "Happy birthday! May this year be filled with endless possibilities and new adventures.",
            "May your birthday bring you the courage to chase your dreams and the strength to overcome any challenge.",
            "Happy birthday! Celebrate and cherish the amazing person you've become.",
            "Wishing you a birthday that's as beautiful and bright as your smile.",
            "On your special day, I wish you all the love and happiness in the world. Happy birthday!",
            "Here's to another year of making cherished memories. Happy birthday!",
            "Happy birthday! May your heart be filled with joy and your life with love.",
            "Wishing you a year filled with love, success, and all the things that make you happy.",
            "Happy birthday! May this day be a reflection of all the goodness you bring into others' lives.",
            "Count your blessings, not just on your birthday, but every day. Happy birthday!",
            "Sending you warmest wishes and lots of love on your birthday. Enjoy your special day!",
            "Happy birthday! May this year be a stepping stone to your dreams and aspirations.",
            "Wishing you a day filled with laughter, love, and the company of good friends. Happy birthday!",
            "Another year wiser, another year filled with amazing adventures. Happy birthday!",
            "Happy birthday! May your heart be filled with joy and your soul with contentment.",
            "Wishing you a birthday that's as sweet and wonderful as you are. Enjoy your day!",
            "May your birthday be the beginning of a new and exciting chapter in your life. Happy birthday!",
            "Happy birthday! May your special day be surrounded by the love of family and friends.",
            "Wishing you happiness, health, and prosperity on your birthday. Have a fantastic day!",
            "Another year of being fabulous! Happy birthday!",
            "Happy birthday! May this year bring you closer to your dreams and aspirations.",
            "Wishing you a day filled with love, laughter, and the fulfillment of all your dreams.",
            "May your birthday be the start of a journey full of love, adventure, and success. Happy birthday!",
            "Happy birthday! May this year be filled with love, laughter, and blessings.",
            "Wishing you a birthday that's as wonderful and special as you are. Enjoy your day!",
            "On your special day, I wish you all the best that life has to offer. Happy birthday!",
            "Happy birthday! May this year be a bright and shining chapter in your life story.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday!",
            "May your birthday be a reminder of how much you are loved and appreciated. Happy birthday!",
            "Happy birthday! May this year be filled with exciting opportunities and beautiful moments.",
            "Wishing you a birthday filled with love, laughter, and unforgettable memories.",
            "On your special day, I send you warm wishes for a bright and successful future. Happy birthday!",
            "Happy birthday! May this year be a journey of growth, happiness, and fulfillment.",
            "Wishing you a day of celebration and a year of blessings. Happy birthday!",
            "May your birthday be as special and extraordinary as you are. Enjoy your day!",
            "Happy birthday! May this year be filled with love, laughter, and dreams come true.",
            "Wishing you a birthday that's as beautiful and radiant as your smile.",
            "On your special day, I wish you all the happiness and success in the world. Happy birthday!",
            "Happy birthday! May this year be filled with love, joy, and all the things that matter most.",
            "Wishing you a day filled with laughter, love, and the company of good friends. Happy birthday!",
            "Another year of life, another reason to be grateful. Happy birthday!",
            "Happy birthday! May this year be a stepping stone to your dreams and aspirations.",
            "Wishing you a birthday filled with love, laughter, and the fulfillment of all your dreams.",
            "May your birthday be the beginning of a new and exciting chapter in your life. Happy birthday!",
            "Happy birthday! May your special day be surrounded by the love of family and friends.",
            "Wishing you happiness, health, and prosperity on your birthday. Have a fantastic day!",
            "Another year of being fabulous! Happy birthday!",
            "Happy birthday! May this year bring you closer to your dreams and aspirations.",
            "Wishing you a day filled with love, laughter, and the fulfillment of all your dreams.",
            "May your birthday be the start of a journey full of love, adventure, and success. Happy birthday!",
            "Happy birthday! May this year be filled with love, laughter, and blessings.",
            "Wishing you a birthday that's as wonderful and special as you are. Enjoy your day!",
            "On your special day, I wish you all the best that life has to offer. Happy birthday!",
            "Happy birthday! May this year be a bright and shining chapter in your life story.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday!",
            "May your birthday be a reminder of how much you are loved and appreciated. Happy birthday!",
            "Happy birthday! May this year be filled with exciting opportunities and beautiful moments.",
            "Wishing you a birthday filled with love, laughter, and unforgettable memories.",
            "On your special day, I send you warm wishes for a bright and successful future. Happy birthday!",
            "Happy birthday! May this year be a journey of growth, happiness, and fulfillment.",
            "Wishing you a day of celebration and a year of blessings. Happy birthday!",
            "May your birthday be as special and extraordinary as you are. Enjoy your day!",
            "Happy birthday! May this year be filled with love, laughter, and dreams come true.",
            "Wishing you a birthday that's as beautiful and radiant as your smile.",
            "On your special day, I wish you all the happiness and success in the world. Happy birthday!",
            "Happy birthday! May this year be filled with love, joy, and all the things that matter most.",
            "Wishing you a day filled with laughter, love, and the company of good"

        )
        "category_anniversary" -> listOf(
            "Happy anniversary! May your love continue to grow stronger with each passing year.",
            "Congratulations on another year of love and togetherness. Happy anniversary!",
            "Wishing you a day filled with love and cherished memories on your anniversary.",
            "Happy anniversary to a wonderful couple who inspire us all with their love and commitment.",
            "May your anniversary be a celebration of the beautiful journey you've shared together.",
            "Congratulations on reaching another milestone in your love story. Happy anniversary!",
            "Happy anniversary! Here's to many more years of love, laughter, and happiness.",
            "Wishing you a day filled with love, joy, and the company of your beloved. Happy anniversary!",
            "May the love you share continue to blossom and bring you endless joy. Happy anniversary!",
            "Happy anniversary to a couple who proves that true love withstands the test of time.",
            "Wishing you all the happiness and love in the world on your anniversary. Congratulations!",
            "Happy anniversary! May your bond of love grow even stronger in the years to come.",
            "Congratulations on another year of love, laughter, and cherished memories. Happy anniversary!",
            "Wishing you a day filled with love and happiness as you celebrate your anniversary.",
            "Happy anniversary to a couple whose love story continues to inspire and touch our hearts.",
            "May your anniversary be a reminder of the precious moments you've shared together.",
            "Happy anniversary! May your love continue to shine bright like a beacon in the night.",
            "Congratulations on finding true love and happiness in each other. Happy anniversary!",
            "Wishing you a day filled with love, romance, and the joy of being in each other's arms. Happy anniversary!",
            "Happy anniversary to a couple who brings love and joy wherever they go.",
            "May your love continue to grow and your bond continue to strengthen. Happy anniversary!",
            "Wishing you a lifetime of love, laughter, and happiness as you celebrate your anniversary.",
            "Happy anniversary! May your love story be a source of inspiration to others.",
            "Congratulations on another year of love, commitment, and beautiful memories. Happy anniversary!",
            "Wishing you a day filled with love, appreciation, and the warmth of each other's embrace. Happy anniversary!",
            "Happy anniversary to a couple who proves that love only gets better with time.",
            "May your love continue to be a shining example for all of us. Happy anniversary!",
            "Congratulations on reaching another milestone in your journey of love. Happy anniversary!",
            "Wishing you a day filled with love, joy, and the company of your soulmate. Happy anniversary!",
            "Happy anniversary! May your love continue to blossom like a beautiful flower.",
            "May your anniversary be a celebration of the love you've shared and the life you've built together.",
            "Congratulations on another year of love, happiness, and wonderful memories. Happy anniversary!",
            "Wishing you a day of love and romance as you celebrate your anniversary.",
            "Happy anniversary to a couple whose love continues to inspire and uplift those around them.",
            "May your love story be filled with joy, laughter, and countless precious moments. Happy anniversary!",
            "Congratulations on another year of love and happiness. Happy anniversary!",
            "Wishing you a day filled with love, appreciation, and the promise of a bright future. Happy anniversary!",
            "Happy anniversary to a couple who exemplifies the true meaning of love and devotion.",
            "May your love continue to be a beacon of hope and inspiration to others. Happy anniversary!",
            "Congratulations on reaching another milestone in your journey of love. Happy anniversary!",
            "Wishing you a day filled with love, joy, and the company of your beloved. Happy anniversary!",
            "Happy anniversary! May your love continue to grow and flourish with each passing year.",
            "May your anniversary be a celebration of the love and happiness you share.",
            "Congratulations on another year of love, laughter, and cherished memories. Happy anniversary!",
            "Wishing you a day of love and romance as you celebrate your anniversary.",
            "Happy anniversary to a couple who continues to show us the beauty of a loving relationship.",
            "May your love story be filled with joy, laughter, and countless precious moments. Happy anniversary!",
            "Congratulations on another year of love and happiness. Happy anniversary!",
            "Wishing you a day filled with love, appreciation, and the promise of a bright future. Happy anniversary!",
            "Happy anniversary to a couple who exemplifies the true meaning of love and devotion.",
            "May your love continue to be a beacon of hope and inspiration to others. Happy anniversary!",
            "Congratulations on reaching another milestone in your journey of love. Happy anniversary!",
            "Wishing you a day filled with love, joy, and the company of your beloved. Happy anniversary!",
            "Happy anniversary! May your love continue to grow and flourish with each passing year.",
            "May your anniversary be a celebration of the love and happiness you share.",
            "Congratulations on another year of love, laughter, and cherished memories. Happy anniversary!",
            "Wishing you a day of love and romance as you celebrate your anniversary.",
            "Happy anniversary to a couple who continues to show us the beauty of a loving relationship.",
            "May your love story be filled with joy, laughter, and countless precious moments. Happy anniversary!",
            "Congratulations on another year of love and happiness. Happy anniversary!",
            "Wishing you a day filled with love, appreciation, and the promise of a bright future. Happy anniversary!",
            "Happy anniversary to a couple who exemplifies the true meaning of love and devotion.",
            "May your love continue to be a beacon of hope and inspiration to others. Happy anniversary!",
            "Congratulations on reaching another milestone in your journey of love. Happy anniversary!",
            "Wishing you a day filled with love, joy, and the company of your beloved. Happy anniversary!",
            "Happy anniversary! May your love continue to grow and flourish with each passing year.",
            "May your anniversary be a celebration of the love and happiness you share.",
            "Congratulations on another year of love, laughter, and cherished memories. Happy anniversary!",
            "Wishing you a day of love and romance as you celebrate your anniversary.",
            "Happy anniversary to a couple who continues to show us the beauty of a loving relationship.",
            "May your love story be filled with joy, laughter, and countless precious moments. Happy anniversary!",
            "Congratulations on another year of love and happiness. Happy anniversary!",
            "Wishing you a day filled with love, appreciation, and the promise of a bright future. Happy anniversary!",
            "Happy anniversary to a couple who exemplifies the true meaning of love and devotion.",
            "May your love continue to be a beacon of hope and inspiration to others. Happy anniversary!",
            "Congratulations on reaching another milestone in your journey of love. Happy anniversary!",
            "Wishing you a day filled with love, joy, and the company of your beloved. Happy anniversary!",
            "Happy anniversary! May your love continue to grow and flourish with each passing year.",
            "May your anniversary be a celebration of the love and happiness you share.",
            "Congratulations on another year of love, laughter, and cherished memories. Happy anniversary!",
            "Wishing you a day of love and romance as you celebrate your anniversary.",
        )

        "category_health" -> listOf(
            "Wishing you good health and vitality for many years to come. Take care!",
            "May your health be your greatest asset and your well-being always be a priority. Stay healthy!",
            "Sending you positive energy and well wishes for a healthy and vibrant life.",
            "Wishing you a life filled with good health, happiness, and inner peace.",
            "May you always be in the best of health and enjoy the gift of well-being. Take good care of yourself!",
            "Here's to a healthy life filled with laughter, love, and all the things that bring you joy.",
            "Wishing you strength and resilience in your journey towards better health and wellness.",
            "May your days be filled with good health, positivity, and a sense of inner balance.",
            "Sending you warm wishes for good health, happiness, and a life brimming with positivity.",
            "Wishing you a healthy lifestyle that nurtures both your body and soul.",
            "May your health be a source of strength and your well-being a beacon of light.",
            "Sending you healing thoughts and best wishes for a healthier and happier life.",
            "Wishing you the courage to prioritize your health and well-being above all else.",
            "May each day bring you closer to optimal health and a life filled with wellness.",
            "Sending you love, light, and healing vibes for a healthy and fulfilling life.",
            "Wishing you a life of good health, boundless energy, and a heart full of gratitude.",
            "May you be blessed with good health, inner peace, and a heart that knows no bounds.",
            "Sending you well wishes for a healthy and vibrant life that's filled with positivity.",
            "Wishing you the strength to overcome any health challenges that come your way.",
            "May your health journey be marked with progress, resilience, and unwavering hope.",
            "Sending you healing energy and positive thoughts for a healthier and happier tomorrow.",
            "Wishing you a life where your health and happiness are intertwined in perfect harmony.",
            "May you find the balance and motivation to embrace a healthy lifestyle with open arms.",
            "Sending you warm wishes for a healthy body, a clear mind, and a joyful heart.",
            "Wishing you the best of health and the determination to make every day count.",
            "May you be surrounded by love, support, and positivity as you prioritize your health.",
            "Sending you strength, love, and well wishes for a healthy and prosperous life.",
            "Wishing you a life filled with good health, positive vibes, and countless blessings.",
            "May your health journey be filled with progress, perseverance, and a sense of accomplishment.",
            "Sending you healing thoughts and best wishes for a healthy and vibrant life ahead.",
            "Wishing you a future filled with good health, inner peace, and limitless potential.",
            "May you embrace self-care and nurture your health with love, compassion, and mindfulness.",
            "Sending you warm wishes for a healthy and happy life that's full of joy and contentment.",
            "Wishing you the wisdom to prioritize your health and the courage to take action.",
            "May your days be filled with good health, positivity, and a heart full of gratitude.",
            "Sending you love, light, and healing vibes for a healthy and fulfilling life.",
            "Wishing you a life of good health, boundless energy, and a heart full of gratitude.",
            "May you be blessed with good health, inner peace, and a heart that knows no bounds.",
            "Sending you well wishes for a healthy and vibrant life that's filled with positivity.",
            "Wishing you the strength to overcome any health challenges that come your way.",
            "May your health journey be marked with progress, resilience, and unwavering hope.",
            "Sending you healing energy and positive thoughts for a healthier and happier tomorrow.",
            "Wishing you a life where your health and happiness are intertwined in perfect harmony.",
            "May you find the balance and motivation to embrace a healthy lifestyle with open arms.",
            "Sending you warm wishes for a healthy body, a clear mind, and a joyful heart.",
            "Wishing you the best of health and the determination to make every day count.",
            "May you be surrounded by love, support, and positivity as you prioritize your health.",
            "Sending you strength, love, and well wishes for a healthy and prosperous life.",
            "Wishing you a life filled with good health, positive vibes, and countless blessings.",
            "May your health journey be filled with progress, perseverance, and a sense of accomplishment.",
            "Sending you healing thoughts and best wishes for a healthy and vibrant life ahead.",
            "Wishing you a future filled with good health, inner peace, and limitless potential.",
            "May you embrace self-care and nurture your health with love, compassion, and mindfulness.",
            "Sending you warm wishes for a healthy and happy life that's full of joy and contentment.",
            "Wishing you the wisdom to prioritize your health and the courage to take action.",
            "May your days be filled with good health, positivity, and a heart full of gratitude.",
            "Sending you love, light, and healing vibes for a healthy and fulfilling life.",
            "Wishing you a life of good health, boundless energy, and a heart full of gratitude.",
            "May you be blessed with good health, inner peace, and a heart that knows no bounds.",
            "Sending you well wishes for a healthy and vibrant life that's filled with positivity.",
            "Wishing you the strength to overcome any health challenges that come your way.",
            "May your health journey be marked with progress, resilience, and unwavering hope.",
            "Sending you healing energy and positive thoughts for a healthier and happier tomorrow.",
            "Wishing you a life where your health and happiness are intertwined in perfect harmony.",
            "May you find the balance and motivation to embrace a healthy lifestyle with open arms.",
            "Sending you warm wishes for a healthy body, a clear mind, and a joyful heart.",
            "Wishing you the best of health and the determination to make every day count.",
            "May you be surrounded by love, support, and positivity as you prioritize your health.",
            "Sending you strength, love, and well wishes for a healthy and prosperous life.",
            "Wishing you a life filled with good health, positive vibes, and countless blessings.",
            "May your health journey be filled with progress, perseverance, and a sense of accomplishment.",
            "Sending you healing thoughts and best wishes for a healthy and vibrant life ahead.",
            "Wishing you a future filled with good health, inner peace, and limitless potential.",
            "May you embrace self-care and nurture your health with love, compassion, and mindfulness.",
            "Sending you warm wishes for a healthy and happy life that's full of joy and contentment.",
            "Wishing you the wisdom to prioritize your health and the courage to take action.",
            "May your days be filled with good health, positivity, and a heart full of gratitude.",
            "Sending you love, light, and healing vibes for a healthy and fulfilling life.",
            "Wishing you a life of good health, boundless energy, and a heart full of gratitude.",
            "May you be blessed with good health, inner peace, and a heart that knows no bounds."
        )

        "category_chirsmas" -> listOf("Wishing you and your family a blessed and joyous Eid Mubarak!",
            "May the divine blessings of Eid bring you happiness, peace, and prosperity.",
            "Eid Mubarak! May this special day be filled with love, laughter, and cherished moments.",
            "Sending you warm wishes and heartfelt greetings on the occasion of Eid. Eid Mubarak!",
            "May the spirit of Eid fill your heart with love, compassion, and forgiveness. Eid Mubarak!",
            "On this auspicious day of Eid, may Allah's blessings be showered upon you and your loved ones.",
            "Wishing you a wonderful Eid surrounded by family, friends, and delicious feasts. Eid Mubarak!",
            "May the joyous occasion of Eid bring you closer to your loved ones and create beautiful memories.",
            "Eid Mubarak! May this day be a reminder of the blessings and love Allah has bestowed upon us.",
            "Sending you warm Eid greetings and hoping your day is filled with smiles and happiness.",
            "May the essence of Eid fill your life with peace, prosperity, and happiness. Eid Mubarak!",
            "Wishing you a blessed and blissful Eid filled with laughter, love, and warmth.",
            "Eid Mubarak! May Allah accept your good deeds and shower you with His divine blessings.",
            "On this Eid, may you find peace and contentment in everything you do. Eid Mubarak!",
            "May the divine blessings of Allah bring you joy and fulfillment on the occasion of Eid. Eid Mubarak!",
            "Eid Mubarak to you and your family! May your hearts be filled with gratitude and humility.",
            "May the holy occasion of Eid bless you with health, happiness, and success. Eid Mubarak!",
            "Sending you warm wishes on Eid and praying for a life full of love and prosperity.",
            "Eid Mubarak! May Allah's blessings be with you today and always.",
            "May the joyous spirit of Eid bring you closer to Allah and fill your life with peace. Eid Mubarak!",
            "Wishing you a beautiful and blessed Eid surrounded by those you love the most.",
            "On this sacred day of Eid, may all your prayers be answered and your dreams come true. Eid Mubarak!",
            "May the colors of Eid fill your life with happiness, love, and hope. Eid Mubarak!",
            "Eid Mubarak! May Allah's blessings illuminate your path and lead you to success.",
            "Wishing you a joyful and prosperous Eid with your family and friends. Eid Mubarak!",
            "On this blessed day of Eid, may you be showered with love, peace, and endless blessings.",
            "Eid Mubarak! May your heart be filled with gratitude and your life be surrounded by joy.",
            "May the spirit of Eid bring you peace, happiness, and a renewed sense of faith. Eid Mubarak!",
            "Wishing you and your family a happy and safe Eid celebration. Eid Mubarak!",
            "On this special day of Eid, may Allah bless you with love, success, and prosperity. Eid Mubarak!",
            "Eid Mubarak! May the beauty of this holy occasion fill your life with goodness and grace.",
            "May the divine blessings of Allah bring you peace and happiness. Eid Mubarak!",
            "Wishing you a blessed and joyous Eid surrounded by loved ones and delicious food. Eid Mubarak!",
            "Eid Mubarak! May Allah's blessings be with you today, tomorrow, and always.",
            "May the spirit of Eid bring you joy, hope, and love. Eid Mubarak!",
            "On this sacred day of Eid, may Allah's mercy and blessings be with you and your family. Eid Mubarak!",
            "Wishing you a blessed and prosperous Eid filled with love, peace, and happiness.",
            "Eid Mubarak! May this day be a reminder of the countless blessings Allah has bestowed upon us.",
            "May the joyous occasion of Eid bring you closer to your loved ones and create beautiful memories.",
            "Sending you warm Eid greetings and hoping your day is filled with smiles and happiness.",
            "May the essence of Eid fill your life with peace, prosperity, and happiness. Eid Mubarak!",
            "Wishing you a blessed and blissful Eid filled with laughter, love, and warmth.",
            "Eid Mubarak! May Allah accept your good deeds and shower you with His divine blessings.",
            "On this Eid, may you find peace and contentment in everything you do. Eid Mubarak!",
            "May the divine blessings of Allah bring you joy and fulfillment on the occasion of Eid. Eid Mubarak!",
            "Eid Mubarak to you and your family! May your hearts be filled with gratitude and humility.",
            "May the holy occasion of Eid bless you with health, happiness, and success. Eid Mubarak!",
            "Sending you warm wishes on Eid and praying for a life full of love and prosperity.",
            "Eid Mubarak! May Allah's blessings be with you today and always.",
            "May the joyous spirit of Eid bring you closer to Allah and fill your life with peace. Eid Mubarak!",
            "Wishing you a beautiful and blessed Eid surrounded by those you love the most.",
            "On this sacred day of Eid, may all your prayers be answered and your dreams come true. Eid Mubarak!",
            "May the colors of Eid fill your life with happiness, love, and hope. Eid Mubarak!",
            "Eid Mubarak! May Allah's blessings illuminate your path and lead you to success.",
            "Wishing you a joyful and prosperous Eid with your family and friends. Eid Mubarak!",
            "On this blessed day of Eid, may you be showered with love, peace, and endless blessings.",
            "Eid Mubarak! May your heart be filled with gratitude and your life be surrounded by joy.",
            "May the spirit of Eid bring you peace, happiness, and a renewed sense of faith. Eid Mubarak!",
            "Wishing you and your family a happy and safe Eid celebration. Eid Mubarak!",
            "On this special day of Eid, may Allah bless you with love, success, and prosperity. Eid Mubarak!",
            "Eid Mubarak! May the beauty of this holy occasion fill your life with goodness and grace.",
            "May the divine blessings of Allah bring you peace and happiness. Eid Mubarak!",
            "Wishing you a blessed and joyous Eid surrounded by loved ones and delicious food. Eid Mubarak!",
            "Eid Mubarak! May Allah's blessings be with you today, tomorrow, and always.",
            "May the spirit of Eid bring you joy, hope, and love. Eid Mubarak!",
            "On this sacred day of Eid, may Allah's mercy and blessings be with you and your family. Eid Mubarak!","Wishing you and your family a Merry Christmas filled with love, joy, and peace.",
            "May the magic of Christmas fill your heart with warmth and happiness. Merry Christmas!",
            "Sending you warm wishes for a joyful and festive Christmas celebration.",
            "May the spirit of Christmas bring you closer to your loved ones and create cherished memories.",
            "Wishing you a Merry Christmas filled with laughter, love, and the company of dear friends.",
            "May the blessings of Christmas be with you today and throughout the coming year.",
            "Merry Christmas! May this special day be a time of reflection, gratitude, and giving.",
            "Sending you heartfelt Christmas greetings and best wishes for a wonderful holiday season.",
            "May the joy and wonder of Christmas fill your home with love and happiness.",
            "Wishing you a Christmas that's as bright and beautiful as your smile.",
            "Merry Christmas! May this season be a time of hope, renewal, and endless possibilities.",
            "On this special day, may you be surrounded by the love and warmth of family and friends. Merry Christmas!",
            "Sending you love, peace, and joy on this magical day of Christmas. Merry Christmas!",
            "May the spirit of Christmas fill your heart with love, compassion, and goodwill.",
            "Wishing you a Christmas filled with laughter, love, and the joy of giving.",
            "Merry Christmas! May this day be a reminder of the true meaning and spirit of the season.",
            "Sending you warm Christmas greetings and hoping your day is filled with blessings and happiness.",
            "May the magic of Christmas bring you happiness and make your dreams come true. Merry Christmas!",
            "Wishing you a Merry Christmas and a New Year filled with hope and endless possibilities.",
            "Merry Christmas! May your heart be filled with the love and wonder of the season.",
            "Sending you warm Christmas wishes and hoping your day is filled with love and joy.",
            "May the spirit of Christmas guide you towards a future filled with peace, love, and harmony. Merry Christmas!",
            "Wishing you a Christmas that's as bright and beautiful as your smile.",
            "Merry Christmas! May this season be a time of hope, renewal, and endless possibilities.",
            "On this special day, may you be surrounded by the love and warmth of family and friends. Merry Christmas!",
            "Sending you love, peace, and joy on this magical day of Christmas. Merry Christmas!",
            "May the spirit of Christmas fill your heart with love, compassion, and goodwill.",
            "Wishing you a Christmas filled with laughter, love, and the joy of giving.",
            "Merry Christmas! May this day be a reminder of the true meaning and spirit of the season.",
            "Sending you warm Christmas greetings and hoping your day is filled with blessings and happiness.",
            "May the magic of Christmas bring you happiness and make your dreams come true. Merry Christmas!",
            "Wishing you a Merry Christmas and a New Year filled with hope and endless possibilities.",
            "Merry Christmas! May your heart be filled with the love and wonder of the season.",
            "Sending you warm Christmas wishes and hoping your day is filled with love and joy.",
            "May the spirit of Christmas guide you towards a future filled with peace, love, and harmony. Merry Christmas!",
            "Wishing you a Christmas that's as bright and beautiful as your smile.",
            "Merry Christmas! May this season be a time of hope, renewal, and endless possibilities.",
            "On this special day, may you be surrounded by the love and warmth of family and friends. Merry Christmas!",
            "Sending you love, peace, and joy on this magical day of Christmas. Merry Christmas!",
            "May the spirit of Christmas fill your heart with love, compassion, and goodwill.",
            "Wishing you a Christmas filled with laughter, love, and the joy of giving.",
            "Merry Christmas! May this day be a reminder of the true meaning and spirit of the season.",
            "Sending you warm Christmas greetings and hoping your day is filled with blessings and happiness.",
            "May the magic of Christmas bring you happiness and make your dreams come true. Merry Christmas!",
            "Wishing you a Merry Christmas and a New Year filled with hope and endless possibilities.",
            "Merry Christmas! May your heart be filled with the love and wonder of the season.",
            "Sending you warm Christmas wishes and hoping your day is filled with love and joy.",
            "May the spirit of Christmas guide you towards a future filled with peace, love, and harmony. Merry Christmas!",
            "Wishing you a Christmas that's as bright and beautiful as your smile.",
            "Merry Christmas! May this season be a time of hope, renewal, and endless possibilities.",
            "On this special day, may you be surrounded by the love and warmth of family and friends. Merry Christmas!",
            "Sending you love, peace, and joy on this magical day of Christmas. Merry Christmas"
        )
        "category_motivation" -> listOf("Wishing you the strength and determination to turn your dreams into reality.",
            "May you find the courage to take bold steps towards your goals and aspirations.",
            "Sending you positive energy and encouragement to keep pushing forward in your journey.",
            "Believe in yourself, for you have the power to overcome any obstacle that comes your way.",
            "May each day bring you closer to your dreams and inspire you to reach new heights.",
            "You are capable of achieving greatness. Embrace your potential and let your light shine.",
            "Stay focused, stay positive, and stay determined. Success is within your reach.",
            "Don't be afraid to fail; be afraid of not trying. Keep striving for your dreams.",
            "Believe in the beauty of your dreams and have the courage to make them a reality.",
            "Your journey may be challenging, but it will be worth it. Keep moving forward!",
            "Every step you take brings you closer to your goals. Keep going and don't give up.",
            "With hard work and perseverance, you can achieve anything you set your mind to.",
            "No dream is too big and no goal is too far. Believe in yourself and go for it!",
            "Your determination and resilience are the keys to unlocking your potential.",
            "Every day is a new opportunity to grow, learn, and become better than yesterday.",
            "In the face of adversity, remember that you are stronger than you know. Keep going!",
            "The journey to success may be tough, but it will be worth it in the end. Keep pushing forward.",
            "Believe in the power of your dreams and let them be your guiding star.",
            "You are capable of achieving greatness. Trust in your abilities and soar high.",
            "Life may be challenging, but remember that challenges are opportunities for growth.",
            "Don't be discouraged by temporary setbacks. Keep your eyes on the prize and persevere.",
            "Your determination and passion are the driving force behind your success. Keep going!",
            "Stay focused, stay positive, and stay true to yourself. You have the power to achieve greatness.",
            "Every day is a chance to make progress and move closer to your dreams. Seize it!",
            "No matter how hard the journey gets, remember that you have the strength to overcome it.",
            "Success comes to those who believe in themselves and work tirelessly towards their goals.",
            "Believe in your abilities and have faith in your journey. You are destined for greatness.",
            "Don't be afraid of challenges; embrace them and use them as stepping stones to success.",
            "The path to success may be filled with obstacles, but they are stepping stones to greatness.",
            "You have the power to shape your destiny. Believe in yourself and work towards your dreams.",
            "Stay committed to your goals and keep striving for excellence. Success will follow.",
            "Believe in the power of your dreams and let them inspire you to take action.",
            "With determination and hard work, you can turn your dreams into reality. Keep going!",
            "No matter how tough the journey gets, remember that you are stronger than you think.",
            "Your perseverance and dedication will lead you to success. Keep moving forward!",
            "Stay focused on your goals and let nothing deter you from achieving them.",
            "Believe in your abilities, for you have the power to create a life of greatness.",
            "Every step you take towards your dreams is a step in the right direction. Keep going!",
            "In the pursuit of your dreams, challenges are inevitable. Embrace them and keep moving forward.",
            "Your determination and resilience are the keys to unlocking your potential.",
            "No matter how challenging the journey, remember that your dreams are worth fighting for.",
            "Believe in yourself, for you are capable of achieving extraordinary things.",
            "The road to success may be long, but with perseverance, you can reach your destination.",
            "Stay focused, stay determined, and stay true to your dreams. Success will be yours.",
            "Your dreams are within reach; all you have to do is believe in yourself and take action.",
            "No dream is too big, and no goal is too ambitious. Believe in yourself and go for it!",
            "With passion and perseverance, you can conquer any challenge that comes your way.",
            "Your journey may be tough, but it will be worth it. Keep pushing forward!",
            "Believe in your abilities and have faith in your journey. You are destined for greatness.",
            "Don't let fear hold you back. Step out of your comfort zone and embrace new challenges.",
            "Your dreams are the seeds of greatness. Nurture them with determination and watch them grow.",
            "No matter how difficult the journey, remember that you are capable of achieving greatness.",
            "Stay focused on your goals, and let nothing distract you from your path to success.",
            "Believe in yourself, for you have the power to achieve anything you set your mind to.",
            "Your dreams are the compass that guides you towards a life of purpose and fulfillment.",
            "No matter how many obstacles you face, never lose sight of your dreams. Keep going!",
            "Stay true to yourself and stay committed to your dreams. Success will be yours.",
            "Your determination and perseverance will lead you to success. Keep moving forward!",
            "Believe in your abilities, and nothing can stand in the way of your dreams.",
            "No matter how tough the journey, remember that you have the strength to overcome it.",
            "Stay focused on your goals and let nothing deter you from achieving them.",
            "Believe in the power of your dreams and let them inspire you to take action.",
            "With determination and hard work, you can turn your dreams into reality. Keep going!",
            "No matter how tough the journey gets, remember that you are stronger than you think.",
            "Your perseverance and dedication will lead you to success. Keep moving forward!",
            "Stay committed to your goals and keep striving for excellence. Success will follow.",
            "Believe in the power of your dreams and let them inspire you to take action.",
            "With determination and hard work, you can turn your dreams into reality. Keep going!",
            "No matter how tough the journey gets, remember that you are stronger than you think.",
            "Your perseverance and dedication will lead you to success. Keep moving forward!",
            "Stay committed to your goals and keep striving for excellence. Success will follow.",
            "Believe in the power of your dreams and let them inspire you to take action.",
            "With determination and hard work, you can turn your dreams into reality. Keep going!",
            "No matter how tough the journey gets, remember that you are stronger than you think.",
            "Your perseverance and dedication will lead you to success. Keep moving forward!",
            "Stay committed to your goals and keep striving for excellence. Success will follow.",
            "Believe in the power of your dreams and let them inspire you to take action.",
            "With determination and hard work, you can turn your dreams into reality. Keep going!",
            "No matter how tough the journey gets, remember that you are stronger than you think.",
            "Your perseverance and dedication will lead you to success. Keep moving forward!",
            "Stay committed to your goals and keep striving for excellence. Success will follow.",

            )
        "category_success" -> listOf(
            "Wishing you all the best on your journey to success. May you reach new heights and achieve greatness.",
            "May your hard work and determination lead you to the path of success. Keep going and never give up.",
            "Sending you positive vibes and best wishes for all your endeavors. Success is within your grasp.",
            "Believe in yourself and your abilities, for you have the power to accomplish remarkable things.",
            "As you strive for success, remember that every step you take brings you closer to your goals.",
            "May your efforts be rewarded with success, and may your dreams become a beautiful reality.",
            "Success is not a destination; it's a journey. Embrace the challenges and enjoy the ride.",
            "Wishing you the courage to chase your dreams and the perseverance to achieve them.",
            "With hard work, determination, and a positive mindset, you are bound to achieve great things.",
            "May success follow you in all your endeavors and bring you joy and fulfillment.",
            "Dream big, work hard, and believe in yourself. Success will be yours.",
            "Sending you best wishes and the hope that success will accompany you at every step.",
            "Success is not just about reaching the top; it's about the journey you take to get there.",
            "May you overcome every obstacle and turn your dreams into reality. Success is yours to claim.",
            "Believe in your abilities and have faith in your journey. Success is waiting for you.",
            "Wishing you the strength to turn your dreams into reality and the determination to never give up.",
            "May your efforts be rewarded with success and your journey be filled with joy and fulfillment.",
            "Success is not achieved overnight; it's a result of hard work, dedication, and perseverance.",
            "May success find you in every corner of your life and bring you prosperity and happiness.",
            "Believe in yourself and your dreams. You have the potential to achieve greatness.",
            "Wishing you the vision to see your goals, the courage to chase them, and the persistence to achieve them.",
            "Success is not just about the destination; it's about the growth and learning that happen along the way.",
            "May every challenge you face become an opportunity for success and personal growth.",
            "Sending you positive energy and best wishes as you embark on the journey to success.",
            "May success embrace you and lead you to a future filled with happiness and contentment.",
            "Believe in your abilities and have confidence in your journey. Success is well within your reach.",
            "Wishing you the strength to overcome obstacles and the wisdom to make every decision count.",
            "May your passion and determination lead you to the pinnacle of success. Keep going!",
            "Success is not a result of luck; it's a consequence of hard work, perseverance, and dedication.",
            "Sending you warm wishes and the hope that success will be with you every step of the way.",
            "May you have the courage to pursue your dreams and the tenacity to achieve them.",
            "Believe in yourself, and success will believe in you. You have all it takes to succeed.",
            "Wishing you the perseverance to never give up and the resilience to overcome any challenge.",
            "Success is not measured by what you achieve, but by the person you become in the process.",
            "May success be the reward for your efforts and the inspiration for your future endeavors.",
            "Believe in the power of your dreams, and they will lead you to the path of success.",
            "Wishing you the strength to face challenges, the wisdom to make decisions, and the perseverance to never give up.",
            "May you be blessed with the courage to chase your dreams and the dedication to achieve them.",
            "Success is not just about reaching the top; it's about the impact you make on the journey.",
            "Sending you positive vibes and best wishes as you strive for success in all your endeavors.",
            "May your journey be filled with opportunities for growth, learning, and success.",
            "Believe in yourself and your dreams, for you have the power to make them a reality.",
            "Wishing you the determination to overcome obstacles and the courage to take risks.",
            "Success is not defined by the world's standards but by your own sense of accomplishment.",
            "May your hard work and dedication lead you to success and fulfillment in all aspects of life.",
            "Believe in your abilities, for they are the stepping stones to your success.",
            "Wishing you the strength to persevere in the face of challenges and the wisdom to make every step count.",
            "May success be the driving force behind your actions and the inspiration for your dreams.",
            "Success is not just about the destination; it's about the person you become on the journey.",
            "Sending you positive energy and the hope that success will be your constant companion.",
            "May your determination and perseverance lead you to the doors of success and fulfillment.",
            "Believe in yourself and your abilities, for they hold the key to your success.",
            "Wishing you the courage to take risks, the strength to overcome obstacles, and the resilience to never give up.",
            "Success is not about achieving perfection; it's about making progress and moving forward.",
            "May you find joy and satisfaction in the pursuit of your dreams and the journey to success.",
            "Believe in your potential, for you have the power to achieve greatness.",
            "Wishing you the perseverance to keep going even when the going gets tough.",
            "Success is not just about the end result; it's about the effort and determination you put in.",
            "May you have the vision to see your goals, the passion to pursue them, and the grit to achieve them.",
            "Sending you positive vibes and best wishes for success in all your future endeavors.",
            "May you be blessed with the strength to face challenges and the wisdom to learn from them.",
            "Believe in your dreams, for they hold the key to a future filled with success and fulfillment.",
            "Wishing you the courage to step out of your comfort zone and embrace new opportunities for success.",
            "Success is not about being the best; it's about being better than you were yesterday.",
            "May you find joy and fulfillment in the pursuit of your dreams and the journey to success.",
            "Believe in your abilities and have faith in your journey. Success is waiting for you.",
            "Wishing you the strength to turn your dreams into reality and the determination to never give up.",
            "May your efforts be rewarded with success and your journey be filled with joy and fulfillment.",
            "Success is not achieved overnight; it's a result of hard work, dedication, and perseverance.",
            "May success follow you in all your endeavors and bring you joy and fulfillment.",
            "Believe in yourself and your abilities, for you have the power to accomplish remarkable things.",
            "As you strive for success, remember that every step you take brings you closer to your goals.",
            "May your efforts be rewarded with success, and may your dreams become a beautiful reality.",
            "Success is not a destination; it's a journey. Embrace the challenges and enjoy the ride.",
            "Wishing you the courage to chase your dreams and the perseverance to achieve them.",
            "With hard work, determination, and a positive mindset, you are bound"

        )
        "father" -> listOf(
            "Happy birthday, Dad! Thank you for being my rock, my inspiration, and my guiding light. May this year bring you all the love and happiness you deserve.",
            "To the greatest father in the world, happy birthday! Your love and support have shaped me into the person I am today. I'm forever grateful to have you as my dad.",
            "Wishing you a fantastic birthday, Dad! Your wisdom, kindness, and unconditional love have been a constant source of strength in my life. I hope this day brings you joy and laughter.",
            "Happy birthday, Dad! You are my hero and role model. Your love and encouragement have made me believe in myself and achieve great things. I love you endlessly.",
            "Sending you warm birthday wishes, Dad! Your presence in my life is a gift that I cherish every day. May your birthday be as special and wonderful as you are.",
            "Happy birthday to the best dad in the world! Your love and guidance have been a blessing in my life. May your birthday be filled with abundant blessings and happiness.",
            "On your special day, Dad, I want to express my deep gratitude for everything you've done for me. Your love and sacrifices have made my life beautiful. Have a wonderful day!",
            "Wishing you a happy birthday, Dad! Your words of wisdom and unwavering support have always been my driving force. May this day be filled with joy and laughter.",
            "Happy birthday, Dad! You are the epitome of strength, kindness, and love. I feel blessed to call you my father and my friend.",
            "To the world's best dad, happy birthday! Your love, care, and guidance have been a source of inspiration for me. I hope this year brings you immense happiness.",
            "Sending you warm birthday wishes, Dad! You've been my guiding light through thick and thin. May this day be filled with joy and love.",
            "Happy birthday to the most amazing father! Your love and support have given me the confidence to face any challenge. I hope this year brings you endless joy and fulfillment.",
            "Dad, you've been my pillar of strength and the best teacher in life. Happy birthday! May your day be filled with love and happiness.",
            "Wishing you a happy birthday, Dad! Your presence in my life is a gift that I cherish every day. May your birthday be filled with joy and happiness.",
            "Happy birthday to the most wonderful father! Your love and care have made my life truly special. I hope this day brings you all the happiness you deserve.",
            "Dad, you are my hero and my best friend. Happy birthday! Your love and guidance have shaped me into the person I am today.",
            "Sending you heartfelt birthday wishes, Dad! Your love and support have been my anchor in stormy seas. I hope this year brings you happiness and prosperity.",
            "Happy birthday, Dad! Your wisdom, kindness, and love have made a world of difference in my life. I'm blessed to call you my father.",
            "Wishing you a fantastic birthday, Dad! Your presence and love make every day brighter. May this year be filled with happiness and success.",
            "Happy birthday to the most amazing father! Your love and support have given me the confidence to face any challenge. I hope this year brings you endless joy and fulfillment.",
            "Dad, you've been my pillar of strength and the best teacher in life. Happy birthday! May your day be filled with love and happiness.",
            "Sending you warm birthday wishes, Dad! You've been my guiding light through thick and thin. May this day be filled with joy and love.",
            "Happy birthday, Dad! You are the epitome of strength, kindness, and love. I feel blessed to call you my father and my friend.",
            "On your special day, Dad, I want to thank you for all the love, care, and sacrifices you've made for me. You are my superhero. Happy birthday!",
            "Wishing you a happy birthday, Dad! Your love and presence make every day brighter. May this year be filled with blessings and prosperity.",
            "Happy birthday to the greatest dad in the world! Your love and guidance have been a blessing in my life. May your birthday be as special as you are.",
            "On your birthday, Dad, I want you to know how much you mean to me. You've been my hero and role model. I love you more than words can express.",
            "Sending you heartfelt birthday wishes, Dad! Your love and support have been the foundation of my life. May this year bring you all the happiness and success you deserve.",
            "Happy birthday, Dad! Your love, strength, and wisdom have been a guiding light in my life. I hope this day is as wonderful as you are.",
            "Wishing you a fantastic birthday, Dad! Your presence and love make every day special. May this year be filled with joy and laughter.",
            "Happy birthday to the best father in the world! Your kindness, love, and care have made me a better person. I am truly blessed to have you as my dad.",
            "Dad, on your birthday, I want to thank you for being my rock and my biggest supporter. I love you to the moon and back. Have a wonderful birthday!",
            "Sending you warm birthday wishes, Dad! Your love and guidance have been my source of strength and inspiration. May your birthday be as amazing as you are.",
            "Happy birthday, Dad! Your love and support have given me the confidence to chase my dreams. I'm grateful for all that you do.",
            "Wishing you a happy birthday, Dad! Your presence in my life is a gift that I cherish every day. May your birthday be filled with joy and happiness.",
            "Happy birthday to the most wonderful father! Your love and care have made my life truly special. I hope this day brings you all the happiness you deserve.",
            "Dad, you are my hero and my best friend. Happy birthday! Your love and guidance have shaped me into the person I am today.",
            "Sending you heartfelt birthday wishes, Dad! Your love and support have been my anchor in stormy seas. I hope this year brings you happiness and prosperity.",
            "Happy birthday, Dad! Your wisdom, kindness, and love have made a world of difference in my life. I'm blessed to call you my father.",
            "Wishing you a fantastic birthday, Dad! Your presence and love make every day brighter. May this year be filled with happiness and success.",
            "Happy birthday to the most amazing father! Your love and support have given me the confidence to face any challenge. I hope this year brings you endless joy and fulfillment.",
            "Dad, you've been my pillar of strength and the best teacher in life. Happy birthday! May your day be filled with love and happiness.",
            "Sending you warm birthday wishes, Dad! You've been my guiding light through thick and thin. May this day be filled with joy and love.",
            "Happy birthday, Dad! You are the epitome of strength, kindness, and love. I feel blessed to call you my father and my friend."

        )
        "bestFriend" -> listOf(
            "Happy birthday to my incredible best friend! May this special day be filled with joy, laughter, and wonderful memories.",
            "Wishing you a day full of happiness and a year ahead filled with blessings, my dear best friend. Happy birthday!",
            "To my partner in crime and my confidant, happy birthday! I'm grateful to have you as my best friend.",
            "Happy birthday, bestie! May this year be even more amazing and unforgettable than the last.",
            "Sending you the warmest birthday wishes, my dearest best friend. Thank you for being a constant source of joy in my life.",
            "Happy birthday to the one who knows me better than anyone else. Here's to many more years of friendship and adventure!",
            "To my ride or die, happy birthday! Thank you for always having my back and being there through thick and thin.",
            "Wishing you a day filled with love, laughter, and all the things you cherish most. Happy birthday, my best friend!",
            "Cheers to another year of friendship and unforgettable moments. Happy birthday, my dear best friend!",
            "On your special day, I want you to know how much you mean to me. You are not just my best friend but family. Happy birthday!",
            "Happy birthday to the person who makes every day brighter and every moment memorable. You are simply the best.",
            "To the person who brings so much joy and laughter into my life, happy birthday! Here's to many more years of happiness together.",
            "May your birthday be as wonderful and extraordinary as you are, my dear best friend. Happy birthday!",
            "Wishing you a day filled with all the love and happiness you deserve, my best friend. Happy birthday!",
            "Happy birthday to my partner in crime and the one who knows me better than anyone else. Here's to another fantastic year together.",
            "To my incredible best friend, may this year bring you all the success and happiness your heart desires. Happy birthday!",
            "Sending you the warmest birthday wishes, my dear best friend. Thank you for being by my side through thick and thin.",
            "Happy birthday to my forever friend. Here's to another year of adventures and making unforgettable memories together.",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, bestie!",
            "To my amazing best friend, may this birthday be the start of the best year yet. Happy birthday!",
            "Happy birthday to the person who knows me inside out and still chooses to be my best friend. You are truly one of a kind.",
            "Cheers to another year of laughter, adventures, and endless fun. Happy birthday, my dear best friend!",
            "On your special day, I want you to know how much I cherish our friendship. You are a blessing in my life. Happy birthday!",
            "Happy birthday to my favorite person and the one who makes life so much more enjoyable. Thank you for being my best friend.",
            "Wishing you a day filled with love, happiness, and all the things you hold dear. Happy birthday, bestie!",
            "To the person who has stood by me through it all, happy birthday! I'm grateful to have you as my best friend.",
            "Happy birthday to my partner in crime and the one who makes every day brighter. Here's to another fantastic year together.",
            "Wishing you a day filled with laughter, love, and all the things that bring you joy. Happy birthday, my dear best friend!",
            "To my incredible best friend, may this year be filled with success, happiness, and countless blessings. Happy birthday!",
            "Sending you the warmest birthday wishes, my dear best friend. You are truly a gift in my life.",
            "Happy birthday to the person who knows me better than I know myself. You are not just my best friend but my soulmate.",
            "To my amazing best friend, may this birthday be the beginning of the best year yet. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, bestie!",
            "Happy birthday to my forever friend. Here's to another year of adventures and unforgettable memories together.",
            "On your special day, I want to express how much your friendship means to me. You are my rock, my confidant, and my biggest supporter. Happy birthday!",
            "Happy birthday to the person who brings so much joy and happiness into my life. You are truly one of a kind.",
            "To the one who has been with me through thick and thin, happy birthday! You are more than a friend; you are family.",
            "Wishing you a day filled with love, laughter, and all the things that bring you joy. Happy birthday, my dear best friend!",
            "To my incredible best friend, may this year be filled with success, happiness, and endless blessings. Happy birthday!",
            "Sending you the warmest birthday wishes, my dear best friend. Thank you for being a constant source of joy in my life.",
            "Happy birthday to the person who knows me better than anyone else. You are not just my best friend but my soulmate.",
            "To my amazing best friend, may this birthday be the start of the best year yet. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, bestie!",
            "Happy birthday to my partner in crime and the one who makes every day brighter. Here's to another fantastic year together.",
            "On your special day, I want to express how much your friendship means to me. You are my rock, my confidant, and my biggest supporter. Happy birthday!",
            "Happy birthday to the person who brings so much joy and happiness into my life. You are truly one of a kind.",
            "To the one who has been with me through thick and thin, happy birthday! You are more than a friend; you are family.",
            "Wishing you a day filled with love, laughter, and all the things that bring you joy. Happy birthday, my dear best friend!",
            "To my incredible best friend, may this year be filled with success, happiness, and endless blessings. Happy birthday!",
            "Sending you the warmest birthday wishes, my dear best friend. Thank you for being a constant source of joy in my life.",
            "Happy birthday to the person who knows me better than anyone else. You are not just my best friend but my soulmate.",
            "To my amazing best friend, may this birthday be the start of the best year yet. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, bestie!",
            "Happy birthday to my partner in crime and the one who makes every day brighter. Here's to another fantastic year together."
        )
        "bf&gf" -> listOf(
            "Happy birthday to the love of my life! You bring so much joy and happiness into my world, and I'm grateful for every moment we share.",
            "To the most amazing boyfriend, happy birthday! You make my heart skip a beat, and I'm blessed to have you by my side.",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who makes my heart sing. You are not just my boyfriend but my best friend and soulmate.",
            "Sending you the warmest birthday wishes, my love. Thank you for being the rock in my life and for loving me unconditionally.",
            "Happy birthday, sweetheart! You are the reason for my smiles, and I'm excited to celebrate this special day with you.",
            "To my loving boyfriend, may this year be filled with endless blessings and happiness. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who makes every day brighter. Your love and care make life more meaningful.",
            "To my incredible boyfriend, may this birthday be the start of the best year yet. You deserve all the happiness in the world.",
            "Sending you the warmest birthday wishes, my dear boyfriend. You are truly a gift in my life.",
            "Happy birthday to the person who knows me inside out and still chooses to love me. You are my everything.",
            "To my amazing boyfriend, may this birthday be the beginning of the best year yet. I love you more with each passing day.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who stole my heart. You are not just my boyfriend but the love of my life.",
            "To the man who makes my heart race, happy birthday! I'm grateful for every moment we share together.",
            "Sending you the warmest birthday wishes, my love. You make every day better with your presence.",
            "Happy birthday, sweetheart! You are my rock and my strength. Thank you for being the best boyfriend.",
            "To my loving boyfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible boyfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Sending you the warmest birthday wishes, my dear boyfriend. You are the light of my life.",
            "Happy birthday to the person who makes my heart skip a beat. You are not just my boyfriend but my soulmate.",
            "To my amazing boyfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the man who has captured my heart, happy birthday! Thank you for being the best boyfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving boyfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my boyfriend but my soulmate.",
            "To my amazing boyfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear boyfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible boyfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the man who has captured my heart, happy birthday! Thank you for being the best boyfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving boyfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my boyfriend but my soulmate.",
            "To my amazing boyfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear boyfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible boyfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear boyfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the man who has captured my heart, happy birthday! Thank you for being the best boyfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving boyfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness"
        )
        "bf&gf" -> listOf(
            "Happy birthday to my beautiful girlfriend! You light up my life with your presence, and I'm grateful for every moment we share.",
            "To the most amazing girlfriend, happy birthday! You make my heart skip a beat, and I'm blessed to have you in my life.",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart sing. You are not just my girlfriend but my best friend and soulmate.",
            "Sending you the warmest birthday wishes, my love. Thank you for being the sunshine in my life and for loving me unconditionally.",
            "Happy birthday, sweetheart! You are the reason for my smiles, and I'm excited to celebrate this special day with you.",
            "To my loving girlfriend, may this year be filled with endless blessings and happiness. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes every day brighter. Your love and care make life more meaningful.",
            "To my incredible girlfriend, may this birthday be the start of the best year yet. You deserve all the happiness in the world.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are truly a gift in my life.",
            "Happy birthday to the person who knows me inside out and still chooses to love me. You are my everything.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I love you more with each passing day.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who stole my heart. You are not just my girlfriend but the love of my life.",
            "To the woman who makes my heart race, happy birthday! I'm grateful for every moment we share together.",
            "Sending you the warmest birthday wishes, my love. You make every day better with your presence.",
            "Happy birthday, sweetheart! You are my rock and my strength. Thank you for being the best girlfriend.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my girlfriend but my soulmate.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my girlfriend but my soulmate.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner.","Happy birthday to my beautiful girlfriend! You light up my life with your presence, and I'm grateful for every moment we share.",
            "To the most amazing girlfriend, happy birthday! You make my heart skip a beat, and I'm blessed to have you in my life.",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart sing. You are not just my girlfriend but my best friend and soulmate.",
            "Sending you the warmest birthday wishes, my love. Thank you for being the sunshine in my life and for loving me unconditionally.",
            "Happy birthday, sweetheart! You are the reason for my smiles, and I'm excited to celebrate this special day with you.",
            "To my loving girlfriend, may this year be filled with endless blessings and happiness. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes every day brighter. Your love and care make life more meaningful.",
            "To my incredible girlfriend, may this birthday be the start of the best year yet. You deserve all the happiness in the world.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are truly a gift in my life.",
            "Happy birthday to the person who knows me inside out and still chooses to love me. You are my everything.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I love you more with each passing day.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who stole my heart. You are not just my girlfriend but the love of my life.",
            "To the woman who makes my heart race, happy birthday! I'm grateful for every moment we share together.",
            "Sending you the warmest birthday wishes, my love. You make every day better with your presence.",
            "Happy birthday, sweetheart! You are my rock and my strength. Thank you for being the best girlfriend.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my girlfriend but my soulmate.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving girlfriend, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my girlfriend but my soulmate.",
            "To my amazing girlfriend, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear girlfriend. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible girlfriend, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear girlfriend!",
            "Happy birthday to the one who brings so much happiness and love into my life. You are truly one of a kind.",
            "To the woman who has captured my heart, happy birthday! Thank you for being the best girlfriend and partner."
        )

        "husband" -> listOf(
            "Happy birthday to the love of my life! You make every day brighter, and I'm grateful for the wonderful life we share together.",
            "To my amazing husband, happy birthday! You are not just my partner in life but also my best friend and confidant.",
            "Wishing you a day filled with love, joy, and all the things that bring you happiness. Happy birthday, my dear husband!",
            "Happy birthday, sweetheart! You are the anchor in my life, and I'm blessed to have you as my husband.",
            "Sending you the warmest birthday wishes, my love. Thank you for being the pillar of strength in our family.",
            "Happy birthday to the man of my dreams! You bring so much happiness and love into my world.",
            "To my incredible husband, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, my dear husband!",
            "Happy birthday to the one who makes every day better with his smile. You are not just my husband but my soulmate.",
            "To my loving husband, may this birthday be the start of the best year yet. I love you more with each passing day.",
            "Sending you the warmest birthday wishes, my dear husband. You are the reason for my smiles.",
            "Happy birthday, my rock! Your love and support mean the world to me, and I'm grateful for your presence in my life.",
            "To the man who has my heart, happy birthday! Thank you for being the best husband and father to our children.",
            "Wishing you a day filled with love, laughter, and all the things that make you smile. Happy birthday, my dear husband!",
            "Happy birthday to the one who brings so much love and happiness into my life. You are truly one of a kind.",
            "To the love of my life, happy birthday! You make every moment special, and I cherish our journey together.",
            "Sending you the warmest birthday wishes, my love. Your love and care make everything better.",
            "Happy birthday, my soulmate! You complete me in every way, and I'm blessed to have you as my husband.",
            "To my incredible husband, may this year be filled with success, love, and endless joy. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear husband!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my husband but my forever love.",
            "To my amazing husband, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear husband. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible husband, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear husband!",
            "Happy birthday to the one who brings so much love and happiness into my life. You are truly one of a kind.",
            "To the man who has captured my heart, happy birthday! Thank you for being the best husband and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving husband, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear husband!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my husband but my forever love.",
            "To my amazing husband, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear husband. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible husband, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear husband!",
            "Happy birthday to the one who brings so much love and happiness into my life. You are truly one of a kind.",
            "To the man who has captured my heart, happy birthday! Thank you for being the best husband and partner.",
            "Sending you the warmest birthday wishes, my love. Your presence in my life makes everything brighter.",
            "Happy birthday, sweetheart! You are the reason I smile every day, and I'm grateful for your love and care.",
            "To my loving husband, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, my dear husband!",
            "Happy birthday to the one who makes my heart skip a beat. You are not just my husband but my forever love.",
            "To my amazing husband, may this birthday be the beginning of the best year yet. I cherish our love and cherish you.",
            "Sending you the warmest birthday wishes, my dear husband. You are the light of my life.",
            "Happy birthday to the person who makes my world a better place. I'm excited to create more beautiful memories with you.",
            "To my incredible husband, may this birthday be the start of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, my dear husband!",
            "Happy birthday to the one who brings so much love and happiness into my life. You are truly one of a kind."
        )

        "sister" -> listOf(
            "Happy birthday to my amazing sister! You are not just my sibling but also my best friend and confidante.",
            "To the one who knows me inside out, happy birthday! You bring so much joy and laughter into my life.",
            "Wishing you a day filled with love, laughter, and all the things that make you happy. Happy birthday, dear sister!",
            "Happy birthday, sis! You are my partner in crime, and I'm grateful for the beautiful bond we share.",
            "Sending you the warmest birthday wishes, my dear sister. Thank you for being a constant source of support and love.",
            "Happy birthday to the one who makes my world a better place. You are the best sister anyone could ask for.",
            "To my incredible sister, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, dear sister!",
            "Happy birthday to my partner in mischief! You make life more exciting, and I cherish every moment with you.",
            "To my loving sister, may this birthday be the start of the best year yet. I love you more each day.",
            "Sending you the warmest birthday wishes, my dear sister. You are a ray of sunshine in my life.",
            "Happy birthday, sis! Your presence in my life makes everything better, and I'm blessed to have you.",
            "To the one who has always been there for me, happy birthday! Thank you for being my pillar of strength.",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, dear sister!",
            "Happy birthday to the one who brings so much love and happiness into my life. You are truly one of a kind.",
            "To my partner in laughter, happy birthday! You are not just my sister but also my lifelong friend.",
            "Sending you the warmest birthday wishes, my dear sister. Your love and care are priceless.",
            "Happy birthday to the one who understands me like no one else. I'm grateful for our special bond.",
            "To my incredible sister, may this birthday be the beginning of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear sister!",
            "Happy birthday to the one who lights up my life with her presence. You are the best sister ever!",
            "To my partner in crime and my confidante, happy birthday! I cherish every moment with you.",
            "Sending you the warmest birthday wishes, my dear sister. You are my rock and my inspiration.",
            "Happy birthday, sis! Your love and care are unparalleled, and I'm blessed to have you in my life.",
            "To the one who has been there through thick and thin, happy birthday! You are more than a sister; you are family.",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, dear sister!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To my partner in laughter, happy birthday! You are not just my sister but also my lifelong friend.",
            "Sending you the warmest birthday wishes, my dear sister. Your love and care are priceless.",
            "Happy birthday to the one who understands me like no one else. I'm grateful for our special bond.",
            "To my incredible sister, may this birthday be the beginning of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear sister!",
            "Happy birthday to the one who lights up my life with her presence. You are the best sister ever!",
            "To my partner in crime and my confidante, happy birthday! I cherish every moment with you.",
            "Sending you the warmest birthday wishes, my dear sister. You are my rock and my inspiration.",
            "Happy birthday, sis! Your love and care are unparalleled, and I'm blessed to have you in my life.",
            "To the one who has been there through thick and thin, happy birthday! You are more than a sister; you are family.",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, dear sister!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To my partner in laughter, happy birthday! You are not just my sister but also my lifelong friend.",
            "Sending you the warmest birthday wishes, my dear sister. Your love and care are priceless.",
            "Happy birthday to the one who understands me like no one else. I'm grateful for our special bond.",
            "To my incredible sister, may this birthday be the beginning of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear sister!",
            "Happy birthday to the one who lights up my life with her presence. You are the best sister ever!",
            "To my partner in crime and my confidante, happy birthday! I cherish every moment with you.",
            "Sending you the warmest birthday wishes, my dear sister. You are my rock and my inspiration.",
            "Happy birthday, sis! Your love and care are unparalleled, and I'm blessed to have you in my life.",
            "To the one who has been there through thick and thin, happy birthday! You are more than a sister; you are family.",
            "Wishing you a day filled with love, laughter, and all the things that bring you happiness. Happy birthday, dear sister!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To my partner in laughter, happy birthday! You are not just my sister but also my lifelong friend.",
            "Sending you the warmest birthday wishes, my dear sister. Your love and care are priceless.",
            "Happy birthday to the one who understands me like no one else. I'm grateful for our special bond.",
            "To my incredible sister, may this birthday be the beginning of an amazing journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear sister!",
            "Happy birthday to the one who lights up my life with her presence. You are the best sister ever!",
            "To my partner in crime and my confidante, happy birthday! I cherish every moment with you."
        )
        "mother" -> listOf(
            "Happy birthday to the most amazing mom! You are my rock and my guiding light, and I'm grateful for your love and support.",
            "To the woman who means the world to me, happy birthday! Your presence in my life is a true blessing.",
            "Wishing you a day filled with love, joy, and all the things that make you happy. Happy birthday, dear mom!",
            "Happy birthday, Mom! You are the heart of our family, and I'm thankful for all the sacrifices you've made for us.",
            "Sending you the warmest birthday wishes, my dear mother. Thank you for being my constant source of strength.",
            "Happy birthday to the one who always puts others before herself. You are a true inspiration, Mom.",
            "To my incredible mother, may this year be filled with love, success, and endless happiness. Happy birthday!",
            "Wishing you a day filled with love and all the things you cherish most. Happy birthday, dear mom!",
            "Happy birthday, Mom! Your love and care are unparalleled, and I'm blessed to have you as my mother.",
            "To the one who has nurtured and shaped me, happy birthday! Thank you for your unconditional love.",
            "Sending you the warmest birthday wishes, my dear mother. You are my role model and my hero.",
            "Happy birthday to the one who knows me better than anyone else. Your love is a treasure I cherish every day.",
            "To my amazing mother, may this birthday be the start of the best year yet. I love you more with each passing day.",
            "Wishing you a day filled with love, laughter, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To the woman who has given her all for her family, happy birthday! You are the heart and soul of our home.",
            "Sending you the warmest birthday wishes, my dear mother. Your love is the foundation of our happiness.",
            "Happy birthday, Mom! Your strength and resilience inspire me to be a better person every day.",
            "To my incredible mother, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who has supported me through thick and thin. Your love is my guiding light.",
            "To the one who has sacrificed so much for her family, happy birthday! Your love is a gift beyond measure.",
            "Sending you the warmest birthday wishes, my dear mother. You are my anchor, and I'm grateful for your presence.",
            "Happy birthday, Mom! Your love is the thread that holds our family together, and I'm thankful for your guidance.",
            "To my amazing mother, may this birthday be the start of an incredible journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who has given me everything. Your love is a treasure I hold close to my heart.",
            "To the woman who has shaped my life, happy birthday! You are my source of strength and inspiration.",
            "Sending you the warmest birthday wishes, my dear mother. Your love and care are the essence of our family.",
            "Happy birthday, Mom! Your wisdom and kindness have made me a better person, and I'm blessed to have you.",
            "To my incredible mother, may this year be filled with love, success, and endless joy. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To the woman who has given her all for her family, happy birthday! You are the heart and soul of our home.",
            "Sending you the warmest birthday wishes, my dear mother. Your love is the foundation of our happiness.",
            "Happy birthday, Mom! Your strength and resilience inspire me to be a better person every day.",
            "To my incredible mother, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who has supported me through thick and thin. Your love is my guiding light.",
            "To the one who has sacrificed so much for her family, happy birthday! Your love is a gift beyond measure.",
            "Sending you the warmest birthday wishes, my dear mother. You are my anchor, and I'm grateful for your presence.",
            "Happy birthday, Mom! Your love is the thread that holds our family together, and I'm thankful for your guidance.",
            "To my amazing mother, may this birthday be the start of an incredible journey ahead. I love you to the moon and back.",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who has given me everything. Your love is a treasure I hold close to my heart.",
            "To the woman who has shaped my life, happy birthday! You are my source of strength and inspiration.",
            "Sending you the warmest birthday wishes, my dear mother. Your love and care are the essence of our family.",
            "Happy birthday, Mom! Your wisdom and kindness have made me a better person, and I'm blessed to have you.",
            "To my incredible mother, may this year be filled with love, success, and endless joy. Happy birthday!",
            "Wishing you a day filled with love, laughter, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who brings so much love and joy into my life. You are truly one of a kind.",
            "To the woman who has given her all for her family, happy birthday! You are the heart and soul of our home.",
            "Sending you the warmest birthday wishes, my dear mother. Your love is the foundation of our happiness.",
            "Happy birthday, Mom! Your strength and resilience inspire me to be a better person every day.",
            "To my incredible mother, may this year be filled with success, love, and countless blessings. Happy birthday!",
            "Wishing you a day filled with love, joy, and all the things that make you smile. Happy birthday, dear mom!",
            "Happy birthday to the one who has supported me through thick and thin. Your love is my guiding light."
        )
        else -> emptyList() //
    }
}