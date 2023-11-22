package fr.vocaltech.streaming.database.utils

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class DeduplicatePlaylist {
    companion object {
        private lateinit var playlistSet: MutableSet<Triple<String, String, String>>

        @JvmStatic
        fun main(args: Array<String>) {
            val assetsDir = Path("").absolutePathString().plus("/app/src/debug/assets/")
            val inputFile = assetsDir.plus("PLAYLIST_20231120_2106_update.txt")
            val outputFile = assetsDir.plus("PLAYLIST_20231120_2106_update.out.txt")

            playlistSet = linkedSetOf()

            try {
                val br = BufferedReader(FileReader(inputFile))
                val bw = BufferedWriter(FileWriter(outputFile))

                //
                // read each line from inputFile and
                // add it into playlistSet
                //
                br.useLines {
                    it.forEach { currentLine ->
                        val isAdded = addCurrentLineIntoSet(currentLine)

                        if (! isAdded)
                            println("Found duplicate with currentLine: $currentLine")
                    }
                }

                //
                // write playlistSet into outputFile
                //
                val playlistSetIterator = playlistSet.iterator()
                while (playlistSetIterator.hasNext()) {
                    bw.appendLine(playlistSetIterator.next().toString())
                }

                bw.close()
                br.close()

            } catch (ioExc: IOException) {
                ioExc.printStackTrace()
            }
        }

        private fun addCurrentLineIntoSet(currentLine: String): Boolean {
            val tokens = currentLine.split(" - ")
            return playlistSet.add(Triple(tokens[0], tokens[1], tokens[2]))
        }
    }
}