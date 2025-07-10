package com.hoshi.weather

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform