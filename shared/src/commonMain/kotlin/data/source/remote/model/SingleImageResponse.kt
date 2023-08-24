package data.source.remote.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class SingleImageResponse(
    @SerialName("date")
    val date: String?,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("ratio")
    val ratio: Double?
)