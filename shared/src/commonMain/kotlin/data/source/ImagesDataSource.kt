package data.source

import data.source.remote.model.ImagesResponse

interface ImagesDataSource {

    interface Local {
        //data can be shown from local or cache storage
    }

    interface Remote {
        suspend fun getImages(pageNumber: Int): ImagesResponse
    }
}