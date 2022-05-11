package spg.tetrux.view

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import spg.tetrux.utility.ResourceManager
import spg.tetrux.model.GlobalObject

class Window : Application() {

	companion object {
		lateinit var SCENE: Scene
		lateinit var STAGE: Stage
	}

	override fun start(primaryStage : Stage) {
		STAGE = primaryStage
		primaryStage.title = "Super cool Tetrux"
		primaryStage.scene = Scene(
			HomePane().apply {
				GlobalObject.homePane = this
			}, 450.0, 600.0
		).apply {
			SCENE = this
		}
		primaryStage.isResizable = false
		primaryStage.show()
	}
}