package spg.tetrux.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import spg.tetrux.utility.ResampledImage

class KeyBindingEntry(name: String, image: Image, hotkey: KeyBindingButton) : BorderPane() {
	init {
		this.left = HBox(
			ImageView(
				ResampledImage.resample(
					image
				)
			).apply {
				this.fitWidth = 15.0
				this.fitHeight = 15.0
			},

			VeryCoolText(
				name
			)
		).apply {
			this.padding = Insets(10.0)
			this.background = Background(
				BackgroundFill(
					Color.web("#35363b"),
					CornerRadii(2.0),
					Insets.EMPTY
				)
			)
			this.spacing = 10.0
			this.alignment = Pos.CENTER_LEFT
		}

		this.center = HBox(
			VeryCoolText(
				"->"
			)
		).apply {
			this.alignment = Pos.CENTER
		}

		this.right = hotkey
	}
}