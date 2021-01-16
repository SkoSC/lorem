package com.skosc.lorem.controller

import com.skosc.lorem.entity.LoremType
import com.skosc.lorem.repository.LoremRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

@Controller
class LoremController @Autowired constructor(private val repository: LoremRepository) {

    fun handle(request: ServerRequest) : Mono<ServerResponse> {
        val type: LoremType = LoremType.match(request.pathVariable(PATH_TYPE))
        return when(type) {
            LoremType.WORDS -> handleWordsRequest(request)
        }
    }

    private fun handleWordsRequest(request: ServerRequest): Mono<ServerResponse> {
        val count = request.queryParam(QUERY_COUNT).map { it.toInt() }.orElse(QUERY_COUNT_DEFAULT)
        return ServerResponse.ok()
            .body(repository.words(count))
    }

    companion object {

        private const val PATH_TYPE = "type"

        private const val QUERY_COUNT = "count"
        private const val QUERY_COUNT_DEFAULT = 4
    }
}