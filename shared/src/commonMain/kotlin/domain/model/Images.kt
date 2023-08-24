package domain.model


data class Images(
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val images: List<SingleImage> = emptyList()
) {
    fun isEmpty() = images.isEmpty()

    operator fun plus(images: Images): Images = Images(
        currentPage = images.currentPage,
        images = this.images + images.images,
        totalPages = images.totalPages
    )
}