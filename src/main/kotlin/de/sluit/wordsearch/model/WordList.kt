package de.sluit.wordsearch.model

import org.springframework.core.io.Resource

class WordList private constructor(words: Set<Word>) {

    private val byLength: Map<Int, List<Word>>

    init {
        require(words.isNotEmpty()) { "Words list must not be empty!" }
        byLength = words.groupBy { it.length }
    }

    fun getRandomOfLength(length: Int): Word? = byLength[length]?.random()
    fun contains(word: Word): Boolean = byLength[word.length]?.contains(word) ?: false
    fun countWithLength(length: Int): Int = byLength[length]?.size ?: 0

    companion object {

        fun from(resource: Resource, allowRepeatLetters: Boolean): WordList =
            resource.inputStream.bufferedReader().useLines { from(it, allowRepeatLetters) }

        fun from(iterable: Sequence<String>, allowRepeatLetters: Boolean): WordList {
            val words = iterable
                .mapNotNull(Word::tryFrom)
                .applyRepeatLettersFilter(allowRepeatLetters)
                .toSet()
            return WordList(words)
        }

        private fun Sequence<Word>.applyRepeatLettersFilter(allowed: Boolean) =
            filter { if (allowed) true else it.letters.size == it.letters.distinct().size }
    }
}
