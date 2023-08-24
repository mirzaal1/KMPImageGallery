package domain.usecase

import data.repository.ImagesRepository
import domain.mapper.ImageMapper
import domain.model.Images
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ImagesFetchUseCase : KoinComponent {

    private val repo: ImagesRepository = get()
    private val mapper: ImageMapper = get()

    fun getImages(pageNumber: Int): Flow<Images> = repo.getImages(pageNumber).map(mapper::mapImage)
}