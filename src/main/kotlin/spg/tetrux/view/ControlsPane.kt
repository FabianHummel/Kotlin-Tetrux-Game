package spg.tetrux.view

import spg.tetrux.control.ButtonHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import spg.tetrux.utility.ResourceManager

class ControlsPane : BorderPane() {

	init {
		this.padding = Insets(20.0)

		this.background = Background(
			BackgroundFill(
				Color.web("#202124"),
				CornerRadii.EMPTY,
				Insets.EMPTY
			)
		)

		this.center = VBox(

			VBox(
				VBox(
					VeryCoolText("Hover over a keybind and press the", 12.0),
					VeryCoolText("desired key to change it.", 12.0),
				).apply {
					this.spacing = 5.0
				},

				VBox(
					VeryCoolText("Pressing Escape while changing a", 12.0),
					VeryCoolText("key results in an unbound action.", 12.0),
				).apply {
					this.spacing = 5.0
				},
			).apply {
				this.spacing = 20.0
			},

			Pane().apply {
				this.background = Background(
					BackgroundFill(
						Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY
					)
				)
				this.prefHeight = 20.0
			},

			KeyBindingEntry(
				"Move Right", Image(
					ResourceManager.getRes("img/binds/MoveRight.png").toExternalForm()
				),
				KeyBindingButton(
					"moveRight"
				)
			),

			KeyBindingEntry(
				"Move Left", Image(
					ResourceManager.getRes("img/binds/MoveLeft.png").toExternalForm()
				),
				KeyBindingButton(
					"moveLeft"
				)
			),

			KeyBindingEntry(
				"Rotate Right", Image(
					ResourceManager.getRes("img/binds/RotateRight.png").toExternalForm()
				),
				KeyBindingButton(
					"rotRight"
				)
			),

			KeyBindingEntry(
				"Rotate Left", Image(
					ResourceManager.getRes("img/binds/RotateLeft.png").toExternalForm()
				),
				KeyBindingButton(
					"rotLeft"
				)
			),

			KeyBindingEntry(
				"Drop Piece", Image(
					ResourceManager.getRes("img/binds/Drop.png").toExternalForm()
				),
				KeyBindingButton(
					"drop"
				)
			)
		).apply {
			this.spacing = 10.0
			this.alignment = Pos.TOP_CENTER
		}

		this.bottom = HBox(
			NiceButton("Back", Image(
				ResourceManager.getRes("img/Back.png").toExternalForm()
			), ButtonHandler() ).apply {
				this.userData = "back"
			},
		).apply {
			this.spacing = 30.0
			this.alignment = Pos.CENTER
		}
	}
}