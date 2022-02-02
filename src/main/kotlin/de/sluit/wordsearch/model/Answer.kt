package de.sluit.wordsearch.model

sealed interface Answer

data class PlayerHasWon(val guess: Word, val letterResults: List<LetterResult>) : Answer {
    constructor(result: GuessResult) : this(result.guess, result.letterResults)
}

data class PlayerHasLost(val guess: Word, val letterResults: List<LetterResult>) : Answer {
    constructor(result: GuessResult) : this(result.guess, result.letterResults)
}
data class WrongWord(val guess: Word, val letterResults: List<LetterResult>) : Answer {
    constructor(result: GuessResult) : this(result.guess, result.letterResults)
}

data class WrongLength(val guess: Word, val expectedLength: Int) : Answer
data class UnknownWord(val guess: Word) : Answer
