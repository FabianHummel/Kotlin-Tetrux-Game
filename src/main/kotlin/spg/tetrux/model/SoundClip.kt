package spg.tetrux.model

import java.io.File
import java.io.InputStream
import java.net.URL

data class SoundClip(val path: URL, val name: String, val volume: Double = 1.0, val loop: Boolean = false)