package com.larsbenedetto.confidentlyincorrect.web.model

class PageInfoRequest(
    val page: Int,
    val size: Int
)

class PageInfoResponse {
    private val page: Int
    private val size: Int
    private val totalPages: Int
    private val totalElements: Long

    constructor(page: Int, size: Int, totalElements: Long) {
        this.page = page
        this.size = size
        this.totalElements = totalElements
        totalPages = if (size == 0) {
            0
        } else {
            // division with round up
            (totalElements + size - 1).toInt() / size
        }
    }

    constructor(
        page: Int, size: Int, totalPages: Int,
        totalElements: Long
    ) {
        this.page = page
        this.size = size
        this.totalPages = totalPages
        this.totalElements = totalElements
    }

    fun hasMorePages(): Boolean {
        return page + 1 < totalPages
    }

    companion object {
        fun <T> from(
            elements: List<T>,
            pageInfo: PageInfoRequest
        ): Pair<List<T>, PageInfoResponse> {
            return Pair(
                elements.chunked(pageInfo.size)[pageInfo.page],
                PageInfoResponse(pageInfo.page, pageInfo.size, elements.size.toLong())
            )
        }
    }
}