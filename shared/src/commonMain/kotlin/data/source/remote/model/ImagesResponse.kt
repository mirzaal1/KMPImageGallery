package data.source.remote.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class ImagesResponse(
    @SerialName("currentPage")
    val currentPage: Int?,
    @SerialName("images")
    val mjImageResponses: List<SingleImageResponse?>?,
    @SerialName("pageSize")
    val pageSize: Int?,
    @SerialName("totalImages")
    val totalImages: Int?,
    @SerialName("totalPages")
    val totalPages: Int?
)