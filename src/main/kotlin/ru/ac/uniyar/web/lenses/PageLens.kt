package ru.ac.uniyar.web.lenses

import org.http4k.core.Request
import org.http4k.lens.Query
import org.http4k.lens.int

data class PageLens(val req: Request) {
    companion object {
        private val queryPageLens = Query.int().defaulted("page", 1)
    }
    fun getPage() = lensOrDefault(queryPageLens, req, 1).let { if (it < 1) 1 else it }
}
