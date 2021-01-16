package com.skosc.lorem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoremApplication

fun main(args: Array<String>) {
    runApplication<LoremApplication>(*args)
}
