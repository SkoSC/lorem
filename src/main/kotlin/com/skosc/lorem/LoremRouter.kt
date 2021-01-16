package com.skosc.lorem

import com.skosc.lorem.controller.LoremController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class LoremRouter {

    @Bean
    fun route(controller: LoremController): RouterFunction<ServerResponse> = RouterFunctions
        .route(RequestPredicates.GET("/{type}"), controller::handle)
}