package de.sluit.wordsearch.model

import de.sluit.wordsearch.cli.green
import de.sluit.wordsearch.cli.red
import de.sluit.wordsearch.cli.yellow

sealed interface LetterResult {
    val letter: Char
    fun coloredLetter(): String
}

data class CorrectPlace(override val letter: Char) : LetterResult {
    override fun coloredLetter() = green(letter)
}

data class IncorrectPlace(override val letter: Char) : LetterResult {
    override fun coloredLetter() = yellow(letter)
}

data class NotPresent(override val letter: Char) : LetterResult {
    override fun coloredLetter() = red(letter)
}
