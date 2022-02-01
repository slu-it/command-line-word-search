package de.sluit.wordsearch.model

sealed interface GuessResult {
    val guesses: List<PreviousGuess>
}

sealed interface FinalGuessResult : GuessResult {
    val solution: Word
}

data class Won(override val guesses: List<PreviousGuess>, override val solution: Word) : FinalGuessResult
data class Lost(override val guesses: List<PreviousGuess>, override val solution: Word) : FinalGuessResult
data class WrongWord(override val guesses: List<PreviousGuess>) : GuessResult
data class WrongWordLength(override val guesses: List<PreviousGuess>) : GuessResult
data class NotOnList(override val guesses: List<PreviousGuess>) : GuessResult
