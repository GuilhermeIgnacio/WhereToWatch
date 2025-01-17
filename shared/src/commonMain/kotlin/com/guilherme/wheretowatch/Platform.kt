package com.guilherme.wheretowatch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform