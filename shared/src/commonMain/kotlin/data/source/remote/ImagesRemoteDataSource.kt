package data.source.remote

import data.source.ImagesDataSource
import data.source.remote.model.ImagesResponse
import kotlinx.coroutines.withContext
import utils.DispatcherProvider

class ImagesRemoteDataSource constructor(
    private val service: ImagesService,
    private val dispatcher: DispatcherProvider
) : ImagesDataSource.Remote {

    override suspend fun getImages(pageNumber: Int): ImagesResponse =
        withContext(dispatcher.io) {
            service.getImages(pageNumber)
        }
}