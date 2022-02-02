package de.sluit.wordsearch.model

data class GuessResult(
    val guess: Word,
    val letterResults: List<LetterResult>
)
