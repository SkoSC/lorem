package com.skosc.lorem.repository

import com.skosc.lorem.entity.LoremIpsum
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class LoremRepository {

    private val sentences: Mono<List<LoremIpsum>> = fetchSentences()

    private val words: Mono<List<LoremIpsum>> = sentences.splitToWordsCached()

    fun sentences(count: Int): Mono<LoremIpsum> = sentences
        .flatMapIterable { it.shuffled() }
        .take(count.toLong())
        .collectList()
        .map { it.joinToString(separator = SENTENCE_BUILD_DELIMITER, postfix = SENTENCE_BUILD_SUFFIX) }

    fun words(count: Int): Mono<LoremIpsum> = words
        .flatMapIterable { it.shuffled() }
        .take(count.toLong())
        .collectList()
        .map { it.joinToString(separator = WORD_BUILD_DELIMITER, postfix = WORD_BUILD_SUFFIX) }
        .map { it.capitalize() }

    private fun fetchSentences(): Mono<List<String>> = Mono.fromCallable {
        ClassPathResource(RAW_RESOURCE).file.readLines(charset = Charsets.UTF_8)
            .flatMap { it.split(*SENTENCE_DELIMITERS) }
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .distinct()
    }.cache()

    private fun Mono<List<LoremIpsum>>.splitToWordsCached(): Mono<List<LoremIpsum>> = flatMapIterable { it }
        .flatMapIterable { it.split(*WORD_DELIMITERS) }
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .distinct()
        .collectList()
        .cache()

    companion object {

        private const val RAW_RESOURCE = "raw.txt"

        private const val SENTENCE_BUILD_DELIMITER = ". "
        private const val SENTENCE_BUILD_SUFFIX = "."

        private const val WORD_BUILD_DELIMITER = " "
        private const val WORD_BUILD_SUFFIX = "."

        private val SENTENCE_DELIMITERS = arrayOf(".", "\n")

        private val WORD_DELIMITERS = arrayOf(" ", ", ", ";")
    }
}