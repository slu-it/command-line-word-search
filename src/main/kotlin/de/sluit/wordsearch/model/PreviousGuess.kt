package de.sluit.wordsearch.model

data class PreviousGuess(
    val guess: Word,
    val letterResults: List<LetterResult>
)
