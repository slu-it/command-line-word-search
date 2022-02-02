package de.sluit.wordsearch.game

import de.sluit.wordsearch.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GameTests {

    @Test
    fun abc() {
        val wordList = WordList.from(sequenceOf("ABCDEF"), sequenceOf("WORDLE"), false)
        val word = Word("WORDLE")
        val game = Game(word, wordList)

        val guess1 = Word("ABCDEF")
        val letterResults1 = listOf(
            NotPresent('A'),
            NotPresent('B'),
            NotPresent('C'),
            CorrectPlace('D'),
            IncorrectPlace('E'),
            NotPresent('F'),
        )

        val guess2 = Word("WORDLE")
        val letterResults2 = listOf(
            CorrectPlace('W'),
            CorrectPlace('O'),
            CorrectPlace('R'),
            CorrectPlace('D'),
            CorrectPlace('L'),
            CorrectPlace('E'),
        )

        assertThat(game.guess(guess1)).isEqualTo(WrongWord(guess1, letterResults1))
        assertThat(game.guess(guess2)).isEqualTo(PlayerHasWon(guess2, letterResults2))
    }
}
