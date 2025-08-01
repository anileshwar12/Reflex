package com.acdevs.reflex.data.model

data class Task(
    val id: Int,
    val category: String,
    val title: String,
    val desc: String,
    val iconName: String,
    val isEnabled: Boolean = false,
    val isFavourite: Boolean = false
    //val usersUsed: Int = 0

)
