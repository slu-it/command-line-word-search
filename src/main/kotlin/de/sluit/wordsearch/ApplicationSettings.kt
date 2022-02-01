package de.sluit.wordsearch

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("settings")
data class ApplicationSettings(
    val wordList: String,
    val wordLength: Int,
    val allowRepeatLetters: Boolean
)
