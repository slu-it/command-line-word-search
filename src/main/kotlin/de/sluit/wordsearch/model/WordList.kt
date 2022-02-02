package de.sluit.wordsearch.model

import org.springframework.core.io.Resource

class WordList private constructor(baseWords: Set<Word>, searchWords: Set<Word>) {

    private val validWords: Set<Word>
    private val searchWordsByLength: Map<Int, List<Word>>

    init {
        require(baseWords.isNotEmpty()) { "Base words set must not be empty!" }
        require(searchWords.isNotEmpty()) { "Search words set must not be empty!" }

        validWords = baseWords + searchWords
        searchWordsByLength = searchWords.groupBy { it.length }
    }

    fun getRandomOfLength(length: Int): Word? = searchWordsByLength[length]?.random()
    fun countWithLength(length: Int): Int = searchWordsByLength[length]?.size ?: 0

    fun exists(word: Word): Boolean = validWords.contains(word)

    companion object {

        fun from(baseWords: Resource, searchWords: Resource, allowRepeatLetters: Boolean): WordList =
            WordList(baseWords.read(allowRepeatLetters), searchWords.read(allowRepeatLetters))

        fun from(baseWords: Sequence<String>, searchWords: Sequence<String>, allowRepeatLetters: Boolean): WordList =
            WordList(baseWords.read(allowRepeatLetters), searchWords.read(allowRepeatLetters))

        private fun Resource.read(allowRepeatLetters: Boolean): Set<Word> =
            inputStream.bufferedReader().useLines { lines -> lines.read(allowRepeatLetters) }

        private fun Sequence<String>.read(allowRepeatLetters: Boolean): Set<Word> =
            map(String::trim)
                .mapNotNull(Word::tryFrom)
                .applyRepeatLettersFilter(allowRepeatLetters)
                .toSet()

        private fun Sequence<Word>.applyRepeatLettersFilter(allowed: Boolean) =
            filter { if (allowed) true else it.letters.size == it.letters.distinct().size }
        
    }
}
