#!/usr/bin/env groovy

def tallLetters  = 'ABCDEFGHIJKLMNOPQRSTUVWXYZbdfghjklpqty'
def shortLetters = 'aceimnorsuvwxz'

def testWords = ["aardvark", "bachelorhood", "challenging", "denominational", "electrocardiograms"]

testWords.each { word ->

    def letters = word.toCharArray().toList()*.toString()

    def topLetters    = []
    def bottomLetters = []

    letters.each {
        topLetters    << "."
        bottomLetters << "."
    }

    letters.each { letter ->

        if (tallLetters.contains(letter)) {
            def firstAvailable = topLetters.indexOf(".")
            topLetters[firstAvailable] = " "
            bottomLetters[firstAvailable] = letter
        } else if (shortLetters.contains(letter)) {
            def firstPossible = topLetters.findLastIndexOf { shortLetters.contains(it) }
            if (firstPossible == -1) {
                def firstAvailable = topLetters.indexOf(".")
                topLetters[firstAvailable] = letter
            } else if (bottomLetters[firstPossible] == ".") {
                bottomLetters[firstPossible] = letter
            } else {
                def nextAvailable = topLetters.indexOf(".")
                topLetters[nextAvailable] = letter
            }
        } else {
            throw new Exception("Can't find character: ${letter}")
        }

    }

    topLetters.removeAll(["."])
    bottomLetters.reverse(true)
    bottomLetters = bottomLetters.dropWhile { it == "." }
    bottomLetters.reverse(true)

    println topLetters
    println bottomLetters

}