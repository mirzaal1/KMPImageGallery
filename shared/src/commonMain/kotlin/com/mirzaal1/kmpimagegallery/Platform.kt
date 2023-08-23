package com.mirzaal1.kmpimagegallery

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform