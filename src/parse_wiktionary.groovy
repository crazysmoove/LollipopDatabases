#!/usr/bin/env groovy

def wiktionaryFile = "c:/Google Drive/projects/words/TEMP-E20130110.tsv"

def outputFile = "c:/Google Drive/projects/words/wiktionary_a-z.tsv"

def seen = []

new File(outputFile).withWriter { writer ->

    new File(wiktionaryFile).eachLine { line ->

        // only write out lines with "English\ta" (English words beginning with lowercase "a"):
        if (line.matches("^English\t[a-z][a-z]*\t.*")) {
            def (language, word, partOfSpeech, definition) = line.split(/\t/)
            //writer << word << "\t" << partOfSpeech << "\t" << definition << "\n"
            // todo: skip over "misspelling of..."
            if (!seen.contains(word)) {
                writer << word << "\n"
                seen << word
            }
        }

    }

}
