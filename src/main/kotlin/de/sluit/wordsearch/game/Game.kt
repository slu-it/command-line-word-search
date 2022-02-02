package de.sluit.wordsearch.game

import de.sluit.wordsearch.model.*

class Game(val solution: Word, val wordList: WordList) {

    private val guessResults = mutableListOf<GuessResult>()

    val wordLength = solution.length
    val maxGuesses = wordLength + 1

    fun guess(guess: Word): Answer =
        when {
            alreadyWon() -> PlayerHasWon(lastGuessResult())
            alreadyLost() -> PlayerHasLost(lastGuessResult())
            lengthMismatch(guess) -> WrongLength(guess, wordLength)
            unknownWord(guess) -> UnknownWord(guess)
            else -> {
                val result = compare(guess, solution)
                guessResults += result

                if (guess == solution) PlayerHasWon(result)
                else if (alreadyLost()) PlayerHasLost(result)
                else WrongWord(result)
            }
        }

    private fun compare(guess: Word, word: Word) = guess.letters.zip(word.letters)
        .map { (guessLetter, wordLetter) ->
            when (guessLetter) {
                wordLetter -> CorrectPlace(guessLetter)
                in word.letters -> IncorrectPlace(guessLetter)
                else -> NotPresent(guessLetter)
            }
        }
        .let { GuessResult(guess, it) }

    private fun alreadyWon(): Boolean = guessResults.lastOrNull()?.guess == solution
    private fun alreadyLost(): Boolean = guessResults.size >= maxGuesses
    private fun lengthMismatch(guess: Word): Boolean = solution.length != guess.length
    private fun unknownWord(guess: Word): Boolean = !wordList.exists(guess)

    private fun lastGuessResult() = guessResults.last()
}
