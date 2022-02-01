package de.sluit.wordsearch.game

import de.sluit.wordsearch.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GameTests {

    @Test
    fun abc() {
        val wordList = WordList.from(sequenceOf("WORDLE", "ABCDEF"), false)
        val word = Word("WORDLE")
        val game = Game(word, wordList)

        val firstGuess = Word("ABCDEF")
        val firstGuessResult = PreviousGuess(
            letterResults = listOf(
                NotPresent('A'),
                NotPresent('B'),
                NotPresent('C'),
                CorrectPlace('D'),
                IncorrectPlace('E'),
                NotPresent('F'),
            ),
            guess = firstGuess
        )

        val secondGuess = Word("WORDLE")
        val secondGuessResult = PreviousGuess(
            letterResults = listOf(
                CorrectPlace('W'),
                CorrectPlace('O'),
                CorrectPlace('R'),
                CorrectPlace('D'),
                CorrectPlace('L'),
                CorrectPlace('E'),
            ),
            guess = secondGuess
        )

        assertThat(game.guess(firstGuess))
            .isEqualTo(WrongWord(listOf(firstGuessResult)))
        assertThat(game.guess(secondGuess))
            .isEqualTo(Won(listOf(firstGuessResult, secondGuessResult), word))
    }
}
