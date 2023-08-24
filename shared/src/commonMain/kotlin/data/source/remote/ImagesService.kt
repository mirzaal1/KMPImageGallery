package data.source.remote

import data.source.remote.model.ImagesResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import utils.Constant.Companion.IMAGES
import utils.Constant.Companion.PAGE

class ImagesService : Api() {
    suspend fun getImages(pageNumber: Int): ImagesResponse = client.get {
        apiUrl(IMAGES)
        parameter(PAGE, pageNumber)
    }.body()
}