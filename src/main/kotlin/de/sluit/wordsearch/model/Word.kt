package de.sluit.wordsearch.model

import org.slf4j.LoggerFactory.getLogger

data class Word(
    private val value: String
) {

    val length: Int
    val letters: List<Char>

    init {
        require(value.matches(regex)) { "Words must match [$regex]!" }
        length = value.length
        letters = value.toList()
    }

    override fun toString() = value

    companion object {
        private val regex = Regex("[A-ZÄÖÜ]+")
        private val log = getLogger(Word::class.java)

        fun tryFrom(value: String): Word? =
            try {
                Word(value.uppercase())
            } catch (e: IllegalArgumentException) {
                log.debug("not a valid word: $value")
                null
            }
    }
}
