package com.example.messagebook

sealed class Screen(val route: String){

    object CategoryScreen:Screen(route = "category")
    object MessageListScreen:Screen(route = "messageList")
    object Splash:Screen(route = "splash")
    object SubCategory:Screen(route = "subcategory")

}
