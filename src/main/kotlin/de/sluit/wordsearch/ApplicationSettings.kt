package de.sluit.wordsearch

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("settings")
data class ApplicationSettings(
    val language: LangaugeSettings,
    val game: GameSettings
) {

    @ConstructorBinding
    data class LangaugeSettings(
        val baseWords: String,
        val searchWords: String
    )

    @ConstructorBinding
    data class GameSettings(
        val wordLength: Int,
        val allowRepeatLetters: Boolean
    )

}
