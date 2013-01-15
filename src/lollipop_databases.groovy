#!/usr/bin/env groovy

def leftLetters = ["a", "b", "c", "d", "e", "f", "g", "q", "r", "s", "t", "v", "w", "x", "z"]
def rightLetters = ["h", "i", "j", "k", "l", "m", "n", "o", "p", "u", "x", "y"]

def allLeft = []
def allRight = []

// file from http://www.sil.org/linguistics/wordlists/english/
new File("c:/Google Drive/projects/words/wordsEn.txt").eachLine { line ->

    def letters = line.toCharArray().toList()*.toString()

    if (letters.every {leftLetters.contains(it)}) {
        allLeft << line
    } else if (letters.every {rightLetters.contains(it)}) {
        allRight << line
    }

}

allLeft.sort(true) { -it.length() }
allRight.sort(true) { -it.length() }

println allLeft
println allRight