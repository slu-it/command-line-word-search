package de.sluit.wordsearch.game

import de.sluit.wordsearch.model.WordList
import org.springframework.stereotype.Component

@Component
class GameFactory(
    private val wordList: WordList
) {

    @Throws(GameCreationException::class)
    fun create(wordLength: Int): Game {
        val word = wordList.getRandomOfLength(wordLength)
            ?: throw GameCreationException("no word of length $wordLength found")
        return Game(word, wordList)
    }

}

class GameCreationException(msg: String) : RuntimeException(msg)
