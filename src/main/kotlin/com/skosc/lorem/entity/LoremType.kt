package com.skosc.lorem.entity

import java.lang.IllegalArgumentException

enum class LoremType(val value: String) {
    WORDS("words");

    companion object {

        fun match(value: String): LoremType = values()
            .firstOrNull { it.value == value }
            ?: throw IllegalArgumentException("Can't find matching lorem type: $value")
    }
}