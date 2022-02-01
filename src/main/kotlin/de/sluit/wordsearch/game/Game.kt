package de.sluit.wordsearch.game

import de.sluit.wordsearch.model.*

class Game(val solution: Word, val wordList: WordList) {

    private val previousGuesses = mutableListOf<PreviousGuess>()

    val wordLength = solution.length
    val maxGuesses = wordLength + 1

    fun guess(guess: Word): GuessResult =
        when {
            alreadyWon() -> Won(previousGuesses, solution)
            alreadyLost() -> Lost(previousGuesses, solution)
            lengthMismatch(guess) -> WrongWordLength(previousGuesses)
            notOnList(guess) -> NotOnList(previousGuesses)
            else -> {
                previousGuesses += compare(guess, solution)
                if (guess == solution) Won(previousGuesses, solution)
                else if (alreadyLost()) Lost(previousGuesses, solution)
                else WrongWord(previousGuesses)
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
        .let { PreviousGuess(guess, it) }

    private fun alreadyWon(): Boolean = previousGuesses.lastOrNull()?.guess == solution
    private fun alreadyLost(): Boolean = previousGuesses.size >= maxGuesses
    private fun lengthMismatch(guess: Word): Boolean = solution.length != guess.length
    private fun notOnList(guess: Word): Boolean = !wordList.contains(guess)

}
