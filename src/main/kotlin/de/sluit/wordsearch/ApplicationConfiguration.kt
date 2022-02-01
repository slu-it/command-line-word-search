package de.sluit.wordsearch

import de.sluit.wordsearch.model.WordList
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class ApplicationConfiguration(
    private val settings: ApplicationSettings
) {

    @Bean
    fun wordList(): WordList =
        WordList.from(
            resource = ClassPathResource("/wordlists/${settings.wordList}"),
            allowRepeatLetters = settings.allowRepeatLetters
        )

}
