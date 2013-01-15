#!/usr/bin/env groovy

String word = "word"

String wordFile = "C:/Google Drive/projects/words/wordsEn.txt"
//String ngramFile = "c:/downloads/wordplay/googlebooks-eng-all-2gram-20120701-wo"

def supersets = []

new File(wordFile).eachLine { line ->

    if (line.contains(word)) {
        supersets << line
    }

}

/*
def ngrams = []

new File(ngramFile).eachLine { line ->

    if (line.contains(word) && !line.contains("_")) {
        String ngram = line.substring(0, line.indexOf("\t"))
        if (!ngrams.contains(ngram)) {
            ngrams << ngram
        }
    }

}
*/

println "Superset words:\n${supersets.join('\n')}\n\n"
//println "ngrams:\n${ngrams.join('\n')}"