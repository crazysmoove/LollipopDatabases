#!/usr/bin/env groovy

import groovy.transform.Canonical
import groovy.transform.Field

@Field def topRow     = ["q", "w", "e", "r", "t", "y", "u", "i", "o", "p"]
@Field def middleRow  = ["a", "s", "d", "f", "g", "h", "j", "k", "l"]
@Field def bottomRow  = ["z", "x", "c", "v", "b", "n", "m"]

def allLetters = [topRow, middleRow, bottomRow].flatten()

// populate key maps:
def keyMaps = []

allLetters.each { sourceLetter ->

    ScoredKeyMap keyMap = new ScoredKeyMap(sourceKey: sourceLetter)

    Coordinate sourceCoord = getCoordinate(sourceLetter)

    allLetters.each { targetLetter ->
        Coordinate targetCoord = getCoordinate(targetLetter)
        double distance = calculateDistance(sourceCoord, targetCoord)
        keyMap.targetKeys[targetLetter] = distance
    }

    keyMaps << keyMap

}

wordScores = [:]

new File("c:/Google Drive/projects/words/wordsEn.txt").eachLine { word ->

    def letters = word.toCharArray().toList()*.toString()

    if (letters.size() < 3) {
        return
    }

    double totalDistance = 0
    for (i in 0..letters.size() - 2) {
        def first  = letters[i]
        def second = letters[i+1]
        if (!allLetters.contains(first) || !allLetters.contains(second)) {
            return
        }
        double distance = (keyMaps.find { it.sourceKey == first }).targetKeys[second]
        // don't allow distances > 3
        if (distance > 2) {
            return
        }
        totalDistance += distance
    }

    wordScores[word] = totalDistance / Math.pow(letters.size(), 2)

}

//wordScores = wordScores.sort { -it.value }
wordScores = wordScores.sort { -it.key.size() }
//wordScores = wordScores.findAll { it.value < 5 }

wordScores.each { println it }
//keyMaps.each { println it }

def calculateDistance(Coordinate sourceCoordindate, Coordinate targetCoordinate) {

    return Math.sqrt( (Math.pow(sourceCoordindate.x - targetCoordinate.x, 2)) +
                      (Math.pow(sourceCoordindate.y - targetCoordinate.y, 2)) )

}

def getCoordinate(letter) {

    def matchingRow = [topRow, middleRow, bottomRow].find { it.contains(letter) }

    int x = matchingRow.indexOf(letter)
    int y = topRow.contains(letter) ? 0 :
            middleRow.contains(letter) ? 1 :
            bottomRow.contains(letter) ? 2 : -1

    return  new Coordinate(x: x, y: y)

}

@Canonical
class ScoredKeyMap {
    String sourceKey
    Map<String, Double> targetKeys = [:]
}

@Canonical
class Coordinate {
    int x, y
}
