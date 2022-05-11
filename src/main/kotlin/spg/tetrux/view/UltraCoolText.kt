package spg.tetrux.view

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.PauseTransition
import javafx.animation.TranslateTransition
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.util.Duration
import spg.tetrux.model.GlobalObject
import spg.tetrux.model.SoundClip
import spg.tetrux.utility.ResourceManager

class UltraCoolText(
	private val text: String = "",
	private val size: Double = 13.0,
	private val duration: Duration = Duration.seconds(1.0),
	private val delay: Duration = Duration.seconds(0.0)
) : HBox() {

	companion object {
		val textSound = GlobalObject.soundControl.registerSound(
			SoundClip(
				ResourceManager.getRes(
					"sound/Game End 1.wav"
				), "Game End Text"
			)
		)
	}

	init {
		this.alignment = Pos.CENTER
		this.children.also { list -> run {
			val delay = duration.toSeconds() / text.length
			text.forEachIndexed { i, c -> list.add(
				VeryCoolText(c.toString(), size).apply {
					this.opacity = 0.0
				}.also { text ->
					PauseTransition(
						Duration.seconds(
							i * delay + this@UltraCoolText.delay.toSeconds()
						)
					).apply {
						this.onFinished = EventHandler {
							FadeTransition(Duration.seconds(delay), text).apply {
								fromValue = 0.0
								toValue = 1.0
								interpolator = Interpolator.LINEAR
							}.play()

							TranslateTransition(Duration.seconds(delay), text).apply {
								this.interpolator = Interpolator.EASE_OUT
								this.toY = 10.0
							}.play()

							GlobalObject.soundControl.playSound(
								textSound
							)
						}
					}.play()
				}
			) }
		} }
	}
}