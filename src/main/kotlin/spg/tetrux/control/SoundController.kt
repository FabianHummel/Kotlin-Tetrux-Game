package spg.tetrux.control

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import spg.tetrux.model.SoundClip
import spg.tetrux.utility.Logger
import spg.tetrux.utility.ResourceManager

class SoundController(vararg sounds: SoundClip) {

	private val logger : Logger = Logger.getLogger("Sound-Control")

	/**
	 * A list of all sounds currently playing.
	 */
	private val sounds : MutableList<MediaPlayer> = mutableListOf()

	/**
	 * A list of registered sounds.
	 */
	private val registry : MutableList<MediaPlayer> = mutableListOf()

	val soundMusic = playSound(
		SoundClip(
			ResourceManager.getRes(
				"sound/Tetrus.wav"
			),
			"Main Theme",
			0.3,
			true
		)
	)

	init {
		playAll(*sounds)
	}

	/**
	 * Creates a media player instance based on the given sound clip.
	 * @param sound The sound clip to create the media player instance for.
	 * @return The created media player instance.
	 */
	private fun createMediaPlayer(sound : SoundClip) : MediaPlayer {
		return MediaPlayer(
			Media(
				sound.path.toExternalForm()
			)
		).apply {
			this.volume = sound.volume
			if (sound.loop) {
				this.onEndOfMedia = Runnable {
					this.seek(Duration.ZERO)
					this.play()
					logger.log("Sound stopped - looping.", nl = true)
				}
			} else {
				this.onEndOfMedia = Runnable {
					sounds.remove(this)
					this.stop()
					logger.log("Sound stopped (${sound.name}) - removing from active list.", nl = true)
				}
			}
		}
	}

	/**
	 * Registers a sound for later use.
	 * @param sound The sound to register.
	 * @return The registered sound.
	 */
	fun registerSound(sound : SoundClip) : MediaPlayer {
		logger.log(
			"\uD83C\uDFB5 Registering Sound \uD83C\uDFB5: ${sound.name}", nl = true
		)

		return createMediaPlayer(
			sound
		).apply {
			registry.add(
				this
			)
		}
	}

	/**
	 * Plays a sound and adds it to the sound list.
	 * @param sound The clip to play.
	 * @return The played sound.
	 */
	fun playSound(sound: SoundClip) : MediaPlayer {
		logger.log(
			"\uD83C\uDFB5 Playing sound \uD83C\uDFB5: ${sound.name}", nl = true
		)

		return registerSound(sound).apply {
			this.play()
		}
	}

	/**
	 * Plays a sound that is already registered.
	 * @param registered The registered sound to play.
	 */
	fun playSound(registered: MediaPlayer) {

		val player = /* deepcopy */ registered.clone()

		logger.log(
			"\uD83C\uDFB5 Playing sound \uD83C\uDFB5: ${player.media.source}", nl = true
		)

		sounds.add(
			player.apply {
				this.play()
			}
		)
	}

	/**
	 * Plays all sounds given to the parameter.
	 */
	fun playAll(vararg sounds: SoundClip) : List<MediaPlayer> {
		val list : MutableList<MediaPlayer> = mutableListOf()
		sounds.forEach {
			list.add(
				playSound(it)
			)
		}
		return list
	}

	/**
	 * Registers all sound clips for later use.
	 */
	fun registerAll(vararg sounds: SoundClip) : List<MediaPlayer> {
		val list : MutableList<MediaPlayer> = mutableListOf()
		sounds.forEach {
			list.add(
				registerSound(it)
			)
		}
		return list
	}
}

private fun MediaPlayer.clone() : MediaPlayer {
	return MediaPlayer(
		this@clone.media
	).apply {
		this.volume = this@clone.volume
		this.onEndOfMedia = this@clone.onEndOfMedia
	}
}