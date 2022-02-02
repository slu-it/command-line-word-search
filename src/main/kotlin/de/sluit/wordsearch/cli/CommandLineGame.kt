package de.sluit.wordsearch.cli

import de.sluit.wordsearch.ApplicationSettings
import de.sluit.wordsearch.game.GameFactory
import de.sluit.wordsearch.model.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty("mode", havingValue = "cli")
class CommandLineGame(
    private val settings: ApplicationSettings,
    private val factory: GameFactory,
    private val wordList: WordList
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val game = factory.create(settings.wordLength)

        println("Welcome to Command Line Word Search!")
        println("--------------------------------------------------")
        println(" Allow Repeat Letters = ${settings.allowRepeatLetters}")
        println(" Word List            = ${settings.wordList}")
        println(" Word Length          = ${game.wordLength}")
        println(" Max. Attempts        = ${game.maxGuesses}")
        println(" Possible Words       = ${wordList.countWithLength(game.wordLength)}")
        println("--------------------------------------------------")
        println()
        println("Guess a word with a length of ${game.wordLength}")

        var playing = true
        while (playing) {
            print("> ")
            val input = readLine() ?: return
            val word = Word.tryFrom(input)
            if (word != null) {
                when (val result = game.guess(word)) {
                    is Won -> {
                        println("""You've won! The searched word is "${game.solution}"""")
                        playing = false
                    }
                    is Lost -> {
                        println("""You've lost! The searched word was "${game.solution}"""")
                        playing = false
                    }
                    is WrongWord -> {
                        println("""No, that is not it! Hints: ${formattedHints(result)}""")
                    }
                    is NotOnList -> {
                        println("""No, "$word" is not on the list of valid words!""")
                    }
                    is WrongWordLength -> {
                        println("""Invalid input "$word"! Must be a ${game.wordLength} letter word.""")
                    }
                }
            } else {
                println("invalid input, try again")
            }
        }
    }

    private fun formattedHints(result: GuessResult): String =
        result.guesses.last().letterResults
            .joinToString(prefix = "[", separator = "][", postfix = "]") { it.coloredLetter() }

}
