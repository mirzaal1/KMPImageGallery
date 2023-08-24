package data.repository

import data.source.ImagesDataSource
import data.source.remote.model.ImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ImagesRepository : KoinComponent {

    private val remoteSource: ImagesDataSource.Remote = get()

    fun getImages(pageNumber: Int): Flow<ImagesResponse> = flow {
        emit(
            remoteSource.getImages(pageNumber)
        )
    }
}