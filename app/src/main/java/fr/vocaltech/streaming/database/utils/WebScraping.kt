package fr.vocaltech.streaming.database.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URL

class WebScraping {
    private data class Music(
        var artistName: String = "",
        var albumTitle: String = "",
        var songTitle: String = "",
        var imgUrl: String = "",
        var imgText: String = ""
    )

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val documentUrl = "https://www.smoothjazz.com/smoothjazz-playlist"

            try {
                val document: Document = Jsoup.connect(documentUrl).get()
                val viewContents = document.select("div.view-content")

                //
                // Now Playing Music
                //
                //val nowMusic = nowMusic(viewContents, documentUrl)
                val nowPlayingMusic = documentUrl.nowPlayingMusic(viewContents) // Extension Function
                println("Now Playing")
                println("--------------")
                println("\t $nowPlayingMusic\n")

                //
                // Last hour playlist
                //
                val lastHourElements = viewContents[3].allElements
                val playlistLastHour = documentUrl.playlistLastHour(lastHourElements)

                println("Last hour playlist")
                println("----------------------")

                playlistLastHour.forEach {
                    println("\t ${it.value}")
                }
            } catch(exc: Exception){
                exc.printStackTrace()
            }
        }

        private fun buildImageUrl(documentUrl: String, imgSrc: String): String {
            val url = URL(documentUrl)

            return StringBuilder()
                .append(url.protocol)
                .append("://")
                .append(url.host)
                .append(imgSrc)
                .toString()
        }

        //
        // Now Playing Music
        // <div class="view-content">  ---> 2nd
        //
        private fun String.nowPlayingMusic(viewContents: Elements): Music { // Extension function
            val nowArtistName = viewContents[1].select("span.artist-name").text()
            val nowAlbumTitle = viewContents[1].select("span.album-title").text()
            val nowSongTitle = viewContents[1].select("span.song-title").text()

            val nowImg = viewContents[1].select("img")
            val nowImgUrl = buildImageUrl(this, nowImg.attr("src"))
            val nowImgText = nowImg.attr("alt")

            return Music(
                nowArtistName,
                nowAlbumTitle,
                nowSongTitle,
                nowImgUrl,
                nowImgText
            )
        }

        //
        // Last hour playlist
        // <div class="view-content">  ---> 4th
        //
        private fun String.playlistLastHour(elements: Elements): List<Lazy<Music>>  {
            var i = 0
            var music = Music()

            return elements
                .select("img.image-style-album2x, span.artist-name, span.album-title, span.song-title")
                .mapNotNull { element ->
                    val lazyMusic = lazy {
                        music
                    }

                    if (element.tag().name == "img") { // --- img section ---
                        lazyMusic.value.imgUrl = buildImageUrl(this, element.attr("src"))
                        lazyMusic.value.imgText = element.attr("alt")
                    }

                    when(element.className()) {
                        "artist-name" -> lazyMusic.value.artistName = element.text()
                        "album-title" -> lazyMusic.value.albumTitle = element.text()
                        "song-title" -> lazyMusic.value.songTitle = element.text()
                    }

                    i++

                    if (i == 4) {
                        i = 0
                        music = Music()
                        return@mapNotNull lazyMusic
                    }
                    null
                }
        }
    }
}