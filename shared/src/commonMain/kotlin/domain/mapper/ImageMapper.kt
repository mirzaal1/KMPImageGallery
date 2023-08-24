package domain.mapper

import data.source.remote.model.ImagesResponse
import domain.model.Images
import domain.model.SingleImage
import kotlinx.coroutines.withContext
import utils.DispatcherProvider

class ImageMapper constructor(private val dispatcher: DispatcherProvider) {

    suspend fun mapImage(response: ImagesResponse): Images =
        withContext(dispatcher.default) {
            Images(
                currentPage = response.currentPage ?: 0,
                images = map(response),
                totalPages = response.totalPages ?: 0
            )

        }

    private fun map(response: ImagesResponse): List<SingleImage> =
        response.mjImageResponses?.map {
            SingleImage(
                date = it?.date ?: return@map null,
                imageUrl = it.imageUrl ?: return@map null,
                ratio = it.ratio ?: return@map null
            )
        }?.filterNotNull()
            .orEmpty()
}