package spg.tetrux.control

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import spg.tetrux.model.GlobalObject
import spg.tetrux.model.SoundClip
import spg.tetrux.utility.Logger
import spg.tetrux.utility.ResourceManager
import spg.tetrux.view.ControlsPane
import spg.tetrux.view.GamePane
import spg.tetrux.view.HomePane
import spg.tetrux.view.Window
import java.util.*

class ButtonHandler : EventHandler<MouseEvent> {

	val logger = Logger.getLogger("End Screen")

	override fun handle(event : MouseEvent) {

		GlobalObject.soundControl.playSound(
			SoundClip(
				ResourceManager.getRes(
					"sound/Game Over ${
						Random().nextInt(1, 4)
					}.wav"
				),
				"Game Over Sound"
			)
		)

		when ( (event.source as Node).userData ) {
			"home" -> {
				Window.STAGE.scene = Scene(
					HomePane().apply {
						GlobalObject.homePane = this
					}, 450.0, 600.0
				)

				logger.log("Going Home", nl = true)
			}

			"restart" -> {
				Window.STAGE.scene = Scene(
					GamePane().apply {
						GlobalObject.gamePane = this
					}, 450.0, 600.0
				).apply {
					this.onKeyPressed = GlobalObject.gamePane.inputControl
					this.onMouseClicked = GlobalObject.gamePane.inputControl
				}

				logger.log("Restarting Game", nl = true)
			}

			"controls" -> {
				Window.STAGE.scene = Scene(
					ControlsPane().apply {
						GlobalObject.controlsPane = this
					}, 450.0, 600.0
				)

				logger.log("Controls Screen", nl = true)
			}

			"back" -> {
				Window.STAGE.scene = Scene(
					HomePane().apply {
						GlobalObject.homePane = this
					}, 450.0, 600.0
				)

				logger.log("Going Home", nl = true)
			}

			"resume" -> {
				GlobalObject.gamePane.gridControl.onPause()
			}
		}
	}
}