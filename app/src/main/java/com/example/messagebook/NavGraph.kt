package com.example.messagebook

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable

fun SetUpNavGraph(
    navController: NavHostController
){
    println("navScreenHome")

    NavHost(navController = navController,
        startDestination = Screen.Splash.route
    ){

        composable(
            route = Screen.CategoryScreen.route
        ){
            println("navScreenCate")
            HomePage(navController=navController)
        }
//        composable(
//            route = Screen.MessageListScreen.route
//        ){
//            println("dsvnsd")
//            ListHome(navController = navController)
//        }
        composable(
            route = "${Screen.MessageListScreen.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            categoryId?.let {
                ListHome(navController=navController,categoryId = categoryId)

            }
        }

        composable(
            "${Screen.SubCategory}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            categoryId?.let { category ->
                SubCategory(navController, category)

            }
        }
//        composable(
//
//            route = Screen.SubCategory.route
//        ){
//
////            SubCategory(navController = navController)
//        }

        composable(

            route = Screen.Splash.route
        ){
            println("navScreen")
            SplashScreen(navController = navController)
        }

    }
}