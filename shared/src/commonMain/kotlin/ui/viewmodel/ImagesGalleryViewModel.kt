package ui.viewmodel

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import domain.State
import domain.model.Images
import domain.usecase.ImagesFetchUseCase
import kotlinx.coroutines.flow.*

class ImagesGalleryViewModel(private val fetchUseCase: ImagesFetchUseCase) : KMMViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _images = MutableStateFlow(Images())
    val images: StateFlow<Images> = _images

    init {
        fetchImages()
    }

    fun refreshImages() {
        _images.value = Images()
        fetchImages()
    }

    fun loadMore() {
        with(_images.value) {
            if (currentPage < totalPages) {
                fetchImages(currentPage + 1)
            }
        }
    }

    private fun fetchImages(pageNumber: Int = 1) {
        fetchUseCase.getImages(pageNumber).onStart { _state.value = State.LOADING }
            .onEach { images ->
                if (images.isEmpty()) {
                    _state.value = State.EMPTY
                } else {
                    _state.value = State.DATA
                    _images.value = _images.value + images
                }
            }.catch {
                _state.value = State.ERROR
            }
            .launchIn(viewModelScope.coroutineScope)
    }


}