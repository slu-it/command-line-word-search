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
        val game = factory.create(settings.game.wordLength)

        println("Welcome to Command Line Word Search!")
        println("--------------------------------------------------")
        println(" Word List            = ${settings.language.searchWords}")
        println(" Allow Repeat Letters = ${settings.game.allowRepeatLetters}")
        println(" Word Length          = ${settings.game.wordLength}")
        println()
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
                val answer = game.guess(word)
                playing = handleAnswerAndDetermineIfStillPlaying(answer)
            } else {
                println("invalid input, try again")
            }
        }
    }

    private fun handleAnswerAndDetermineIfStillPlaying(answer: Answer): Boolean =
        when (answer) {
            is PlayerHasWon -> handle(answer)
            is PlayerHasLost -> handle(answer)
            is WrongWord -> handle(answer)
            is WrongLength -> handle(answer)
            is UnknownWord -> handle(answer)
        }

    private fun handle(answer: PlayerHasWon): Boolean {
        println("""${format(answer.letterResults)} is the word! ${green("You've won!")}""")
        return false
    }

    private fun handle(answer: PlayerHasLost): Boolean {
        println("""${format(answer.letterResults)} isn't the word! ${red("You've lost!")}""")
        return false
    }

    private fun handle(answer: WrongWord): Boolean {
        println("""${format(answer.letterResults)} isn't the word!""")
        return true
    }

    private fun handle(answer: WrongLength): Boolean {
        println(""""${answer.guess}" is not a ${answer.expectedLength} letter word!""")
        return true
    }

    private fun handle(answer: UnknownWord): Boolean {
        println("""What's does "${answer.guess}" mean?"""")
        return true
    }

    private fun format(letterResults: List<LetterResult>): String =
        letterResults.joinToString(prefix = "[", separator = "][", postfix = "]") { it.coloredLetter() }
}
