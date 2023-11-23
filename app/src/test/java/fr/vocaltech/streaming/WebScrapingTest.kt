package fr.vocaltech.streaming

import fr.vocaltech.streaming.database.utils.WebScraping.Companion.nowPlayingMusic
import fr.vocaltech.streaming.database.utils.WebScraping.Companion.playlistLastHour
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert.assertEquals
import org.junit.Test

class WebScrapingTest {
    @Test
    fun givenDocumentUrl_whenNowPlayingMusic_thenOk() {
        val documentUrl = "https://www.smoothjazz.com/smoothjazz-playlist"

        try {
            val document: Document = Jsoup.connect(documentUrl).get()
            val viewContents = document.select("div.view-content")

            val nowPlayingMusic = documentUrl.nowPlayingMusic(viewContents)

            println("nowPlayingMusic: $nowPlayingMusic")

        } catch(exc: Exception) {
            exc.printStackTrace()
        }
    }

    @Test
    fun givenDocumentUrl_whenPlaylistLastHour_thenOk() {
        val documentUrl = "https://www.smoothjazz.com/smoothjazz-playlist"

        try {
            val document: Document = Jsoup.connect(documentUrl).get()
            val viewContents = document.select("div.view-content")
            val lastHourElements = viewContents[3].allElements
            val playlistLastHour = documentUrl.playlistLastHour(lastHourElements)

            assertEquals(10, playlistLastHour.size)

            playlistLastHour.forEach {
                println("\t ${it.value}")
            }

        } catch(exc: Exception) {
            exc.printStackTrace()
        }
    }
}